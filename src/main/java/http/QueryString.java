package http;

import java.util.Objects;

public class QueryString {

    private final String key;
    private final String value;

    QueryString(String[] keyAndValue) {
        this.key = keyAndValue[0];
        this.value = keyAndValue[1];
    }

    QueryString(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryString)) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(getKey(), that.getKey()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }

    @Override
    public String toString() {
        return "QueryString{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
