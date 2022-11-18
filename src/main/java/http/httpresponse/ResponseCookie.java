package http.httpresponse;

import java.util.Objects;

public class ResponseCookie {

    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String DEFAULT_PATH = "/";


    private final String key;
    private final String value;
    private final String path;

    public ResponseCookie(String key,
                          String value,
                          String path) {
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public ResponseCookie(String key,
                          String value) {
        this(key, value, DEFAULT_PATH);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseCookie that = (ResponseCookie) o;
        return key.equals(that.key) && value.equals(that.value) && path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, path);
    }

    @Override
    public String toString() {
        return String.join(KEY_VALUE_DELIMITER, key, value) + ";" + "Path=" + path;
    }
}
