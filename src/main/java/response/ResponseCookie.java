package response;

public class ResponseCookie {

    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String DELIMITER = "; ";
    private static final String DEFAULT_PATH = "/";

    private String key;
    private String value;
    private String path;

    public ResponseCookie(String key, String value, String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public static ResponseCookie of(String key, String value) {
        return new ResponseCookie(key, value, DEFAULT_PATH);
    }

    @Override
    public String toString() {
        return String.join(DELIMITER, String.join(KEY_VALUE_DELIMITER, key, value),
            String.join(KEY_VALUE_DELIMITER, "path", path));
    }
}
