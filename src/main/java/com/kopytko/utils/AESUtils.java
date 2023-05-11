package com.kopytko.utils;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public final class AESUtils {

    private static final String PASSWORD = "password";
    private static final String SALT = "12345678";
    private static final IvParameterSpec iv = generateIv();

    public static SecretKey getKeyFromPassword(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }


    public static String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getKeyFromPassword(PASSWORD), iv);
        return Base64.getEncoder()
                .encodeToString(cipher.doFinal(plainText.getBytes()));
    }


    public static String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, getKeyFromPassword(PASSWORD), iv);
        return new String(cipher.doFinal(Base64.getDecoder()
                .decode(cipherText)));
    }
}
