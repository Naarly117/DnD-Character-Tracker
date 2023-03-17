package org.example;

public class FormatHandler {

    // Parameter: s - String to be normalized
    // Returns: lower-cased, space-less version of s
    public static String normalize(String s) {
        return s.toLowerCase().replaceAll("\\s", "");
    }

}
