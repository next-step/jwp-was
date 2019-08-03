package utils;

import javafx.util.Pair;

public class StringParseUtils {
    public static Pair<String, String> makeKeyValuePair(String v, String separator) {
        String[] param = v.split(separator);
        return new Pair<>(param[0], param[1]);
    }
}
