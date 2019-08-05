package model.http;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParameter {
    public static final String KEY_VALUE_SPLITTER = "=";
    public static final Pattern KEY_PATTERN = Pattern.compile("[a-zA-Z0-9\\-_]+");
    public static final Pattern VALUE_PATTERN = Pattern.compile("[a-zA-Z0-9\\-._~%!$'()*+,;=@]+");
    public static final Pattern PATTERN = Pattern.compile(KEY_PATTERN + KEY_VALUE_SPLITTER + VALUE_PATTERN);

    private String key;
    private String value;

    private QueryParameter(String key, String value) {
        this.key = key.toLowerCase();
        this.value = value.toLowerCase();
    }

    public static QueryParameter of(String key, String value) {
        if (!validatePattern(key, value)) throw new IllegalArgumentException("wrong query parameter pattern");
        return new QueryParameter(key, value);
    }

    public static QueryParameter of(String parameter) {
        if (!validatePattern(parameter)) throw new IllegalArgumentException("wrong query parameter pattern");
        String[] parts = parameter.split(KEY_VALUE_SPLITTER);
        return of(parts[0], parts[1]);
    }

    private static boolean validatePattern(String key, String value) {
        Matcher keyMatcher = KEY_PATTERN.matcher(key);
        Matcher valueMatcher = VALUE_PATTERN.matcher(value);

        return keyMatcher.find()
                && key.length() == keyMatcher.group().length()
                && valueMatcher.find()
                && value.length() == valueMatcher.group().length();
    }

    private static boolean validatePattern(String queryString) {
        Matcher matcher = PATTERN.matcher(queryString);
        return matcher.find() && queryString.length() == matcher.group().length();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryParameter that = (QueryParameter) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "QueryParameter{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
