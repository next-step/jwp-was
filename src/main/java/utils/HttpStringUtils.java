package utils;

import java.util.regex.Pattern;

public class HttpStringUtils {
    public static String[] split(String input, String delimiter) {
        return input.split(delimiter);
    }

    public static String splitAndFindByIndex(String inputData, String delimiter, int index) {
        return inputData.split(delimiter)[index];
    }

    public static boolean isPatternMatch(String regex, String input) {
        return Pattern.matches(regex, input);
    }

    public static boolean checkLoginCookie(String cookie) {
        String[] cookies = split(cookie, ";");
        for (String data : cookies) {
            if (data.trim().equals("logined=true")) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }
}
