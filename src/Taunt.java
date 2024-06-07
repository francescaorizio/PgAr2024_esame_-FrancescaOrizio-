import java.util.Scanner;

class Taunt {
    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        String extendedKey = extendKeyToMatchLength(key, plaintext.length());
        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);
            char encChar = (char) ((plainChar + keyChar - 2 * 'a') % 26 + 'a');
            ciphertext.append(encChar);
        }
        return ciphertext.toString();
    }

    private static String extendKeyToMatchLength(String key, int length) {
        StringBuilder extendedKey = new StringBuilder(length);
        int keyLength = key.length();
        for (int i = 0; i < length; i++) {
            extendedKey.append(key.charAt(i % keyLength));
        }
        return extendedKey.toString();
    }
}