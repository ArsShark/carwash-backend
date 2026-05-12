package com.example.carwash.util;

public class Masking {

    /**
     * Маскирует номер телефона, оставляя только последние 4 цифры
     * Пример: +7 (999) 123-45-67 -> +7 (***) ***-**67
     */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) {
            return phone;
        }

        // Удаляем все нецифровые символы
        String digits = phone.replaceAll("\\D", "");

        if (digits.length() < 4) {
            return phone;
        }

        // Оставляем последние 4 цифры, остальное маскируем
        String lastFour = digits.substring(digits.length() - 4);
        StringBuilder masked = new StringBuilder();

        for (int i = 0; i < digits.length() - 4; i++) {
            masked.append("*");
        }
        masked.append(lastFour);

        // Возвращаем в исходном формате (с буквами и символами)
        return applyOriginalFormat(phone, masked.toString());
    }

    /**
     * Маскирует email, оставляя первые и последние символы
     * Пример: user@example.com -> u***@example.com
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }

        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        if (username.length() <= 2) {
            return email;
        }

        String maskedUsername = username.charAt(0) + "***" + username.charAt(username.length() - 1);
        return maskedUsername + "@" + domain;
    }

    /**
     * Маскирует автомобильный номер
     * Пример: А123БВ777 -> А***БВ777
     */
    public static String maskCarNumber(String carNumber) {
        if (carNumber == null || carNumber.length() < 6) {
            return carNumber;
        }

        // Оставляем первую букву и последние 3 цифры региона
        return carNumber.charAt(0) + "***" + carNumber.substring(carNumber.length() - 4);
    }

    // Вспомогательный метод для сохранения формата
    private static String applyOriginalFormat(String original, String maskedDigits) {
        StringBuilder result = new StringBuilder();
        int digitIndex = 0;

        for (char c : original.toCharArray()) {
            if (Character.isDigit(c)) {
                if (digitIndex < maskedDigits.length()) {
                    result.append(maskedDigits.charAt(digitIndex));
                    digitIndex++;
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}