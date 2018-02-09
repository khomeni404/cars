package com.mbl.security.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Copyright &copy; 2017-2018 Soft Engine Inc.
 * <p>
 * Original author: Khomeni
 * Date: 22/11/2017 11:27 AM
 * Last modification by: Khomeni: Khomeni
 * Last modification on 22/11/2017: 22/11/2017 11:27 AM
 * Current revision: 1.0.0: 1.1 $
 * <p>
 * Revision History:
 * ------------------
 */

public class Christopher {
    Cipher eCipher;
    Cipher dCipher;

    /**
     * Pad a string with spaces on the right.
     *
     * @param string String to add spaces
     * @param width  Width of string after padding
     */
    public static String pad(String string, int width) {
        StringBuilder stringBuffer = new StringBuilder(string);
        int space = width - stringBuffer.length();
        while (space-- > 0) {
            stringBuffer.append(' ');
        }

        return stringBuffer.toString();
    }

    public Christopher(String passphrase) {
        int keyWidth = passphrase.length();
        if (keyWidth < 24)
            passphrase = pad(passphrase, 24);
        try {
            byte[] passphraseBytes = passphrase.getBytes();
            DESedeKeySpec keySpec = new DESedeKeySpec(passphraseBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey key = keyFactory.generateSecret(keySpec);

            eCipher = Cipher.getInstance("DESede/ECB/NoPadding");
            dCipher = Cipher.getInstance("DESede/ECB/NoPadding");
            eCipher.init(Cipher.ENCRYPT_MODE, key);
            dCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String str) {
        try {
            int byteLength = str.getBytes("UTF8").length;
            if (byteLength % 8 != 0) {
                byteLength = (byteLength / 8 + 1) * 8;
                str = pad(str, byteLength);
            }
            try {
                byte[] utf8 = str.getBytes("UTF8");
                byte[] enc = eCipher.doFinal(utf8);

                return new sun.misc.BASE64Encoder().encode(enc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (java.io.UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }

        return str;
    }

    public String decrypt(String str) {
        try {
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            byte[] utf8 = dCipher.doFinal(dec);

            return (new String(utf8, "UTF8")).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static void main(String[] args) {
        Base64.Encoder encoder = Base64.getEncoder();
        String normalString = "username:password";
        String encodedString = encoder.encodeToString(
                normalString.getBytes(StandardCharsets.UTF_8) );
        System.out.println("encodedString = " + encodedString);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(encodedString);

        System.out.println(new String(decodedByteArray));
    }
}
