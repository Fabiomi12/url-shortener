package com.example.url.shortener.util;

public class SimpleBase62 {
    private SimpleBase62() {}
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int BASE = ALPHABET.length();
    private static final char PADDING_CHAR = '-';
    private static final int MIN_LENGTH = 6;

    public static String encode(long input) {
        StringBuilder result = new StringBuilder();
        do {
            result.insert(0, ALPHABET.charAt((int) (input % BASE)));
            input /= BASE;
        } while (input > 0);

        // Add padding if necessary
        while (result.length() < MIN_LENGTH) {
            result.insert(0, PADDING_CHAR);
        }

        return result.toString();
    }

    public static long decode(String input) {
        String trimmed = input.replaceAll("^" + PADDING_CHAR + "+", "");
        long result = 0;
        for (int i = 0; i < trimmed.length(); i++) {
            result = result * BASE + ALPHABET.indexOf(trimmed.charAt(i));
        }
        return result;
    }
}
