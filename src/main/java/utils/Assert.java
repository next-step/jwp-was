package utils;

public final class Assert {

    private Assert() {
        throw new AssertionError("'Assert' can not be instanced");
    }

    public static void notEmpty(CharSequence charSequence, String message) {
         if(charSequence == null || charSequence.length() == 0) {
             throw new IllegalArgumentException(message);
         }
    }
    public static void notNull(Object object,
                               String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
