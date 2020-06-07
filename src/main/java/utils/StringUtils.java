package utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

    public static boolean isEmpty(String val) {
        if (val == null || val.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }


}
