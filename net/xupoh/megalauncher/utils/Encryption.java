/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.xupoh.megalauncher.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import net.xupoh.megalauncher.main.Settings;

/**
 *
 * @author Админ
 */
public class Encryption {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static byte[] encrypt(byte[] in) throws Exception {
        return doCrypto(Cipher.ENCRYPT_MODE, Settings.stylesSalt, in);
    }

    public static byte[] decrypt(byte[] in) throws Exception {
        return doCrypto(Cipher.DECRYPT_MODE, Settings.stylesSalt, in);
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] in) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey);
        
        return cipher.doFinal(in);
    }
}
