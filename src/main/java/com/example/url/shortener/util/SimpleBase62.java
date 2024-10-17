package com.example.url.shortener.util;


public class SimpleBase62 {
    private SimpleBase62() {}
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int BASE = ALPHABET.length();
    private static final char PADDING_CHAR = '-';
    private static final int MIN_LENGTH = 6;

    public static String encode(long input) {
        // Apply padding before encoding
        long paddedInput = applyPadding(input);

        StringBuilder result = new StringBuilder();
        do {
            result.insert(0, ALPHABET.charAt((int) (paddedInput % BASE)));
            paddedInput /= BASE;
        } while (paddedInput > 0);

        return result.toString();
    }

    public static long decode(String input) {
        long result = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == PADDING_CHAR) {
                result = result * BASE;
            } else {
                result = result * BASE + ALPHABET.indexOf(c);
            }
        }
        return removePadding(result);
    }

    private static long applyPadding(long input) {
        long paddedInput = input;
        int paddingNeeded = MIN_LENGTH - Long.toString(input, BASE).length();
        for (int i = 0; i < paddingNeeded; i++) {
            paddedInput *= BASE;
        }
        return paddedInput;
    }

    private static long removePadding(long paddedInput) {
        while (paddedInput % BASE == 0 && paddedInput > 0) {
            paddedInput /= BASE;
        }
        return paddedInput;
    }
}
