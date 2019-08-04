package utils;

import java.util.Objects;

public class StringUtils {

    public static boolean isNotBlank(String string) {
        return string != null && !string.isEmpty();
    }

    public static String requireNotBlank(String string, String message) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException(message);
        }

        return string;
    }

    public static String frontSplitWithOrigin(String string, char regex) {
        return splitCharacter(string, regex, true, false);
    }

    public static String endSplit(String string, char regex) {
        return splitCharacter(string, regex, false, true);
    }

    public static String endLastSplit(String string, char regex) {
        return splitLastCharacter(string, regex, false, true);
    }

    /**
     * @return string split by character and choice front or end according to flag
     */
    private static String splitCharacter(String string, char character, boolean front, boolean returnEmptyBlank) {
        int index = Objects.requireNonNull(string, "split string must be not null")
                .indexOf(character);

        if (index < 0) {
            return returnEmptyBlank ? "" : string;
        }

        return front ? string.substring(0, index).trim() : string.substring(index + 1).trim();
    }

    private static String splitLastCharacter(String string, char character, boolean front, boolean returnEmptyBlank) {
        int index = Objects.requireNonNull(string, "split string must be not null")
                .lastIndexOf(character);

        if (index < 0) {
            return returnEmptyBlank ? "" : string;
        }

        return front ? string.substring(0, index).trim() : string.substring(index + 1).trim();
    }

}
