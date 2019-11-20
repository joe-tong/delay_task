package com.example.springboot_demo2.timeout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public final class RingBufferWheel {

    private static final Logger logger = LoggerFactory.getLogger(RingBufferWheel.class);

    public RingBufferWheel() {
    }

    /**
     * default ring buffer size
     */
    private static final int STATIC_RING_SIZE = 64;

    private Object[] ringBuffer;

    private int bufferSize;

    /**
     * business thread pool
     */
    private ExecutorService executorService;

    private AtomicInteger taskSize = new AtomicInteger();

    /***
     * task running sign
     */
    private volatile boolean stop = false;

    private volatile boolean start = false;

    /**
     * total tick times
     */
    private AtomicInteger tick = new AtomicInteger();

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * Create a new delay task ring buffer by default size
     *
     * @param executorService the business thread pool
     */
    public RingBufferWheel(ExecutorService executorService) {
        this.executorService = executorService;
        this.bufferSize = STATIC_RING_SIZE;
        this.ringBuffer = new Object[bufferSize];
    }


    /**
     * Create a new delay task ring buffer by custom buffer size
     *
     * @param executorService the business thread pool
     * @param bufferSize      custom buffer size
     */
    public RingBufferWheel(ExecutorService executorService, int bufferSize) {
        this(executorService);

        if (!powerOf2(bufferSize)) {
            throw new RuntimeException("bufferSize=[" + bufferSize + "] must be a power of 2");
        }
        this.bufferSize = bufferSize;
        this.ringBuffer = new Object[bufferSize];
    }

    /**
     * Add a task into the ring buffer
     *
     * @param task business task extends RingBufferWheel.Task
     */
    public void addTask(Task task) {
        int key = task.getKey();
        Set<Task> tasks = get(key);

        if (tasks != null) {
            int cycleNum = cycleNum(key, bufferSize);
            task.setCycleNum(cycleNum);
            tasks.add(task);
        } else {
            int index = mod(key, bufferSize);
            int cycleNum = cycleNum(key, bufferSize);
            task.setCycleNum(index);
            task.setCycleNum(cycleNum);
            Set<Task> sets = new HashSet<>();
            sets.add(task);
            put(key, sets);
        }

        taskSize.incrementAndGet();

    }

    /**
     * thread safe
     *
     * @return the size of ring buffer
     */
    public int taskSize() {
        return taskSize.get();
    }

    /**
     * Start background thread to consumer wheel timer, it will always run until you call method {@link #stop}
     */
    public void start() {

        if (!start) {
            logger.info("delay task is starting");
            Thread job = new Thread(new TriggerJob());
            job.setName("consumer RingBuffer thread");
            job.start();
            start = true;
        }

    }

    /**
     * Stop consumer ring buffer thread
     *
     * @param force True will force close consumer thread and discard all pending tasks
     *              otherwise the consumer thread waits for all tasks to completes before closing.
     */
    public void stop(boolean force) {
        if (force) {
            logger.info("delay task is forced stop");
            stop = true;
            executorService.shutdownNow();
        } else {
            logger.info("delay task is stopping");
            if (taskSize() > 0) {
                try {
                    lock.lock();
                    condition.await();
                    stop = true;
                } catch (InterruptedException e) {
                    logger.error("InterruptedException", e);
                } finally {
                    lock.unlock();
                }
            }
            executorService.shutdown();
        }


    }


    private Set<Task> get(int key) {
        int index = mod(key, bufferSize);
        return (Set<Task>) ringBuffer[index];
    }

    private void put(int key, Set<Task> tasks) {
        int index = mod(key, bufferSize);
        ringBuffer[index] = tasks;
    }

    private Set<Task> remove(int key) {
        Set<Task> tempTask = new HashSet<>();
        Set<Task> result = new HashSet<>();

        Set<Task> tasks = (Set<Task>) ringBuffer[key];
        if (tasks == null) {
            return result;
        }

        for (Task task : tasks) {
            if (task.getCycleNum() == 0) {
                result.add(task);

                size2Notify();
            } else {
                // decrement 1 cycle number and update origin data
                task.setCycleNum(task.getCycleNum() - 1);
                tempTask.add(task);
            }
        }

        //update origin data
        ringBuffer[key] = tempTask;

        return result;
    }

    private void size2Notify() {
        try {
            lock.lock();
            int size = taskSize.decrementAndGet();
            if (size == 0) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean powerOf2(int target) {
        if (target < 0) {
            return false;
        }
        int value = target & (target - 1);
        if (value != 0) {
            return false;
        }

        return true;
    }

    private int mod(int target, int mod) {
        // equals target % mod
        target = target + tick.get();
        return target & (mod - 1);
    }

    private int cycleNum(int target, int mod) {
        //equals target/mod
        return target >> Integer.bitCount(mod - 1);
    }

    /**
     * An abstract class used to implement business.
     */
    public abstract static class Task extends Thread {


        private int cycleNum;

        private int key;

        @Override
        public void run() {
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getCycleNum() {
            return cycleNum;
        }

        private void setCycleNum(int cycleNum) {
            this.cycleNum = cycleNum;
        }
    }


    private class TriggerJob implements Runnable {

        @Override
        public void run() {
            int index = 0;
            while (!stop) {

                Set<Task> tasks = remove(index);
                for (Task task : tasks) {
                    executorService.submit(task);
                }

                if (++index > bufferSize - 1) {
                    index = 0;
                }

                //Total tick number of records
                tick.incrementAndGet();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    logger.error("InterruptedException", e);
                }
            }

            logger.info("delay task is stopped");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        RingBufferWheel wheel = new RingBufferWheel(executorService);

        for (int i = 0; i < 65; i++) {
            RingBufferWheel.Task task = new Job(i);
            task.setKey(i);
            wheel.addTask(task);
        }
        Thread.sleep(1000000);

    }

    public static class Job extends RingBufferWheel.Task{
        private int num;
        public Job(int num){
            this.num = num;
        }

        @Override
        public void run() {
            logger.info("number={}",num);
        }
    }


}
