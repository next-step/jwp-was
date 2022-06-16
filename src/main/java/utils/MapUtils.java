package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtils {

    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    public static Map<String, String> parse(String str) {
        Map<String, String> map = Arrays.stream(str.split(AMPERSAND))
                .filter(s -> s.contains(EQUAL))
                .map(s -> s.split(EQUAL))
                .filter(split -> split.length > 1)
                .collect(Collectors.toMap(ss -> ss[0], ss -> ss[1]));

        return map;
    }
}
