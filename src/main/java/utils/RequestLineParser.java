package utils;

import java.util.regex.Pattern;

public class RequestLineParser {
    private static final Pattern REQUEST_LINE = Pattern.compile(
            "[A-Z]* {1}\\S* {1}[A-Z]*\\/[0-9|.]+");

    static boolean isRequestLinePattern(String requestLine) {
        return REQUEST_LINE.matcher(requestLine).matches();
    }
}
