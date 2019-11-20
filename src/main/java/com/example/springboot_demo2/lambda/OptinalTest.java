package com.example.springboot_demo2.lambda;

import java.util.Optional;

public class OptinalTest {
    public static void main(String[] args) {
        System.out.println("Optinal of");
        try {
            of();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal ofNullable");
        try {
            ofNullable();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal empty");
        try {
            empty();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal get");
        try {
            get();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal orElse");
        try {
            orElse();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal orElseGet");
        try {
            orElseGet();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal orElseThrow");
        try {
            orElseThrow();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal filter");
        try {
            filter();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal map");
        try {
            map();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal flagmap");
        try {
            flagmap();
        }catch (Exception e){

        }
        System.out.println("=========================");
        System.out.println("Optinal ifpresent");
        try {
            ifpresent();
        }catch (Exception e){

        }

    }

    private static void ifpresent() {
        //ifPresent方法的参数是一个Consumer的实现类，Consumer类包含一个抽象方法，该抽象方法对传入的值进行处理，只处理没有返回值。
        Optional<String> stringOptional = Optional.of("zhangsan");
        stringOptional.ifPresent(e-> System.out.println("我被处理了。。。"+e));
    }

    private static void flagmap() {
        //map方法中的lambda表达式返回值可以是任意类型，在map函数返回之前会包装为Optional。
        //但flatMap方法中的lambda表达式返回值必须是Optionl实例
        Optional<String> stringOptional = Optional.of("zhangsan");
        System.out.println(stringOptional.flatMap(e -> Optional.of("lisi")).orElse("失败"));

        stringOptional = Optional.empty();
        System.out.println(stringOptional.flatMap(e -> Optional.empty()).orElse("失败"));
    }

    private static void map() {
        //如果创建的Optional中的值存在，对该值执行提供的Function函数调用
        //map方法执行传入的lambda表达式参数对Optional实例的值进行修改,修改后的返回值仍然是一个Optional对象
        Optional<String> stringOptional = Optional.of("zhangsan");
        System.out.println(stringOptional.map(e -> e.toUpperCase()).orElse("失败"));

        stringOptional = Optional.empty();
        System.out.println(stringOptional.map(e -> e.toUpperCase()).orElse("失败"));
    }

    private static void filter() {
        //如果创建的Optional中的值满足filter中的条件，则返回包含该值的Optional对象，否则返回一个空的Optional对象
        Optional<String> stringOptional = Optional.of("zhangsan");
        System.out.println(stringOptional.filter(e -> e.length() > 5).orElse("王五"));
        stringOptional = Optional.empty();
        System.out.println(stringOptional.filter(e -> e.length() > 5).orElse("lisi"));
    }

    private static void orElseThrow() {
        //如果创建的Optional中有值存在，则返回此值，否则抛出一个由指定的Supplier接口生成的异常
        Optional<String> stringOptional = Optional.of("张三");
        System.out.println(stringOptional.orElseThrow(CustomException::new));

        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.orElseThrow(CustomException::new));
    }

    private static class CustomException extends RuntimeException {
        private static final long serialVersionUID = -4399699891687593264L;

        public CustomException() {
            super("自定义异常");
        }

        public CustomException(String message) {
            super(message);
        }
    }

    private static void orElseGet() {
        //如果创建的Optional中有值存在，则返回此值，否则返回一个由Supplier接口生成的值
        Optional<String> stringOptional = Optional.of("张三");
        System.out.println(stringOptional.orElseGet(() -> "zhangsan"));

        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.orElseGet(() -> "orElseGet"));
    }

    private static void orElse() {
        //如果创建的Optional中有值存在，则返回此值，否则返回一个默认值
        Optional<String> stringOptional = Optional.of("张三");
        System.out.println(stringOptional.orElse("zhangsan"));

        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.orElse("李四"));
    }

    private static void get() {
        //如果我们创建的Optional对象中有值存在则返回此值，如果没有值存在，则会抛出NoSuchElementException异常。
        Optional<String> stringOptional = Optional.of("张三");
        System.out.println(stringOptional.get());
        Optional<String> emptyOptional = Optional.empty();
        System.out.println(emptyOptional.get());
    }

    private static void empty() {
        //创建一个空的String类型的Optional对象
        Optional<String> emptyOptional = Optional.empty();
    }

    private static void ofNullable() {
        //为指定的值创建Optional对象，不管所传入的值为null不为null，创建的时候都不会报错
        Optional<String> nullOptional1 = Optional.ofNullable(null);
        Optional<String> nullOptional2 = Optional.ofNullable("lisi");
    }

    private static void of() {
        //创建一个值为张三的String类型的Optional
        Optional<String> ofOptional = Optional.of("张三");
        ofOptional.ifPresent(item -> System.out.println(item));
        //如果我们用of方法创建Optional对象时，所传入的值为null，则抛出NullPointerException如下图所示
        Optional<String> nullOptional = Optional.of(null);
    }
}