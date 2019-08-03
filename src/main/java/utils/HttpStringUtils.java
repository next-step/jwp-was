package utils;

public class HttpStringUtils {
    public static String[] split(String input, String delimiter) {
        return input.split(delimiter);
    }

    public static String splitAndGetDataByIndex(String inputData, String delimiter, int index) {
        return inputData.split(delimiter)[index];
    }
}
