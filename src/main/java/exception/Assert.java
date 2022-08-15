package exception;

public final class Assert {

    private Assert() {
        throw new IllegalArgumentException("Utils 클래스는 인스턴스화를 할 수 없습니다.");
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasText(String text, String message) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
