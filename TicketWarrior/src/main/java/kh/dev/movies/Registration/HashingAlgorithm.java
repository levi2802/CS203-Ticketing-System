package kh.dev.movies.Registration;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class HashingAlgorithm {
    public String hashStringUsingSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Initialise message digest with SHA-256
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8)); // computes the hash of the provided input data (in byte array form)

            // Convert byte array into a string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b); // Convert each byte to hexadecimal
                if (hex.length() == 1) {
                    hexString.append('0'); // Add leading zero to make bit two characters long. Ensure the output has a consistent length and format.
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256", e);
        }
    }
}
