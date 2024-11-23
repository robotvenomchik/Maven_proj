package org.example.Homework23;

import com.example.PasswordGenerator;

public class PasswordClient {
    public static void main(String[] args) {
        int passwordLength = 15;

        String generatedPassword = PasswordGenerator.generatePassword(passwordLength);
        System.out.println("Згенерований пароль: " + generatedPassword);


        String securePassword = PasswordGenerator.generateSecurePassword(passwordLength);
        System.out.println("Безпечний пароль: " + securePassword);

        boolean isStrong = PasswordGenerator.isPasswordStrong(securePassword);
        System.out.println("Пароль сильний? " + (isStrong ? "Так" : "Ні"));
    }
}