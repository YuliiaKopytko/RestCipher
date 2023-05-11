package com.kopytko;

import com.kopytko.utils.AESUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AESUtilsTest {

    @Test
    @SneakyThrows
    void testAESCipher() {
        String plainText = "plainText";
        String cipherText = AESUtils.encrypt(plainText);
        String decryptedCipherText = AESUtils.decrypt(cipherText);
        Assertions.assertEquals(plainText, decryptedCipherText);
    }
}
