package com.example.springboot_demo2.apisecurity;

/**
 * @author Chenjing
 * @date 2018/12/29
 */
public class SecretProviderImpl extends AbstractSecretProvider {

    @Override
    public String decrypt(String payload, String key) throws Exception {
        return super.decrypt(payload, key);
    }

    @Override
    public String encrypt(String response, String key) throws Exception {
        return super.encrypt(response, key);
    }
}
