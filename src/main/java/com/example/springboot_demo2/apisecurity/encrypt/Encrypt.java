package com.example.springboot_demo2.apisecurity.encrypt;

/**
 * @author Chenjing
 * @date 2018/12/29
 */
public interface Encrypt {
    String encrypt(String response, String key) throws Exception;
}
