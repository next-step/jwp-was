package utils;

import java.util.Objects;

public class StringUtils {
    public boolean isEmpty(String target) {
        return Objects.isNull(target) || target.length() <= 0;
    }

    public boolean isNotEmpty(String target) {
        return !isEmpty(target);
    }
}
