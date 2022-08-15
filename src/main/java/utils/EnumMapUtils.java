package utils;

import java.util.EnumMap;
import java.util.Map;

public class EnumMapUtils {

    public static <K extends Enum<K>, V> EnumMap<K, V> of(Map<K, V> map) {
        return new EnumMap<K, V>(map);
    }
}
