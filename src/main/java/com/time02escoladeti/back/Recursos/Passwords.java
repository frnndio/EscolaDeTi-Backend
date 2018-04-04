package com.time02escoladeti.back.Recursos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Passwords {
    public static String getHashPassword(String password) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(password.getBytes());

        byte[] byteData = md.digest();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
