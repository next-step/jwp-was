package utils;

import java.util.Map;

public class MapUtils {

    public static <T> void putIfKeyNotBlank(Map<String, T> map, String key, T value) {
        if (key != null && !key.isEmpty()) {
            map.put(key, value);
        }
    }

}
