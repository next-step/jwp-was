package utils;

import javafx.util.Pair;

import java.util.LinkedList;

public class StringParseUtils {
    public static Pair<String, String> makeKeyValuePair(String v, String separator) {
        String[] param = v.split(separator);
        return new Pair<>(param[0], param[1]);
    }

    public static Pair<String, LinkedList<String>> makeKeyMultiValuePair(String v, String separator) {
        String[] param = v.split(separator);
        LinkedList<String> values = new LinkedList<>();
        values.add(param[1]);
        return new Pair<>(param[0], values);
    }
}
