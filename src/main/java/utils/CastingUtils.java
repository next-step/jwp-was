package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastingUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CastingUtils.class);

    private CastingUtils() {
        throw new AssertionError();
    }

    public static <T> T cast(Object obj, Class<T> returnType) {
        if (returnType.isInstance(obj)) {
            return returnType.cast(obj);
        }
        return castStringToReturnType((String) obj, returnType);
    }

    @SuppressWarnings("unchecked")
    private static <T> T castStringToReturnType(String obj, Class<T> returnType) {
        try {
            if (returnType == Boolean.class) {
                return (T) Boolean.valueOf(obj);
            }
            if (returnType == Integer.class) {
                return (T) Integer.valueOf(obj);
            }
        } catch(ClassCastException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }
}
