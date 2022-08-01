package utils;

import java.util.Arrays;
import java.util.Objects;

public final class NullChecker {

    private NullChecker() {
    }

    public static void requireNonNull(Object... objects) {
        Arrays.stream(objects)
            .forEach(Objects::requireNonNull);
    }

}
