package webserver.http.response;

import utils.Assert;

import java.util.Objects;

public final class ResponseCookie {

    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String DEFAULT_PATH = "/";

    private final String key;
    private final String value;
    private final String path;

    private ResponseCookie(String key, String value, String path) {
        Assert.hasText(key, "'key' must not be empty");
        Assert.hasText(value, "'value' must not be empty");
        Assert.hasText(path, "'path' must not be empty");
        this.key = key;
        this.value = value;
        this.path = path;
    }

    public static ResponseCookie of(String key, String value) {
        return new ResponseCookie(key, value, DEFAULT_PATH);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResponseCookie that = (ResponseCookie) o;
        return Objects.equals(key, that.key) && Objects.equals(value, that.value) && Objects.equals(path, that.path);
    }

    @Override
    public String toString() {
        return String.join(KEY_VALUE_DELIMITER, key, value) + ";" +
                "Path=" + path;
    }
}
