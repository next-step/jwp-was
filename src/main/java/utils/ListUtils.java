package utils;

import java.util.List;

public class ListUtils {
    public static <T extends List<U>, U> T merge(T a, T b) {
        a.addAll(b);
        return a;
    }
}
