package com.example.springboot_demo2.apisecurity;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProductProviderImpl implements ProductProvider{


    @Override
    public String getHmacKey(HttpServletRequest request) {
        return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1Zg/cUN+8XXtXQ/DcgXaA78AI\n" +
                "He5x8AaT5Xkv3ftAlrHCQp8ye2kyGJ0gwC8oIjT3oXWyftksPveVehY5T48RuMbU\n" +
                "49HKFuRW3BKEoveUH2Voc6W5DRhngZnjy047jqdpJGw59ZJ8J8+IIdnLuHry2QRl\n" +
                "ItK/j+QtbXDNg2ZylQIDAQAB";
    }

    @Override
    public String getEncryptKey(HttpServletRequest request) {
        return "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALVmD9xQ37xde1dD\n" +
                "8NyBdoDvwAgd7nHwBpPleS/d+0CWscJCnzJ7aTIYnSDALygiNPehdbJ+2Sw+95V6\n" +
                "FjlPjxG4xtTj0coW5FbcEoSi95QfZWhzpbkNGGeBmePLTjuOp2kkbDn1knwnz4gh\n" +
                "2cu4evLZBGUi0r+P5C1tcM2DZnKVAgMBAAECgYEAsVaUDukpsfcaC9gp8wjGF4tL\n" +
                "iyPn5o+nfpMKhNdZOG2aXrXO+QVZdFZH1qrL70pxyd2ZOOV13yG33fQ7IdA7poow\n" +
                "JPlN6x3wCAC+GOyw7nDKkQLwn5W1FFphYTJSUuvWAzjxcVg2KcroUuQzisY6jEpz\n" +
                "fcOiJHet4xo/WSvwFskCQQDuIpE5h0i7iuvHCv/mPoPFbp4iaKh3LgCuIMKCapJ4\n" +
                "PR06iZYDtZj6Qd2o43pME+eDePeh6kXv071lqjg2b7nXAkEAwwHcfgQRk18VlqvR\n" +
                "9B5vvHffuEuXPrhoeXPjsoDyjv3Z1aYW6I4T3UnoqIxj2B6RyHnOYIkv2cSGiIFP\n" +
                "LiDhcwJBAKw7hB2/ovNBvtXvNrYocNXS87yfRXktZICMYSJCQj2Eticoa7+zEDmw\n" +
                "eck0/bcYjlGgpDoPXxXeZxWoz49Pr3ECQCZMTEh3dhgyWWQQ9Jf0Dk++XzKrrdWE\n" +
                "lAgeSX66MmkA0/JQvzkHBkG0jTvk7or4Xfs4aN6usrYbEazncGyHyp8CQQC3OAl3\n" +
                "DfyuK+Oz/ytMu/ciRIxY9uLWMN7Zc1CpSaHrInFDmbYfWhWo1p5clEDZzLvQRNRw";
    }

    @Override
    public String getDecryptKey(HttpServletRequest request) {
        return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1Zg/cUN+8XXtXQ/DcgXaA78AI\n" +
                "He5x8AaT5Xkv3ftAlrHCQp8ye2kyGJ0gwC8oIjT3oXWyftksPveVehY5T48RuMbU\n" +
                "49HKFuRW3BKEoveUH2Voc6W5DRhngZnjy047jqdpJGw59ZJ8J8+IIdnLuHry2QRl\n" +
                "ItK/j+QtbXDNg2ZylQIDAQAB";
    }
}
