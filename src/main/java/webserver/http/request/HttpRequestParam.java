package webserver.http.request;

import java.util.Objects;

public class HttpRequestParam {
    public static final String QUERY_STRING_DELIMITER = "=";
    public static final int QUERY_STRING_SCHEMA_SIZE = 2;
    public static final int QUERY_STRING_SCHEMA_NAME_INDEX = 0;
    public static final int QUERY_STRING_SCHEMA_VALUE_INDEX = 1;
    public static final HttpRequestParam EMPTY = new HttpRequestParam("", "");

    private final String name;
    private final String value;

    private HttpRequestParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static HttpRequestParam from(String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return EMPTY;
        }
        String[] queryStringSchemas = queryString.split(QUERY_STRING_DELIMITER);

        if (queryStringSchemas.length != QUERY_STRING_SCHEMA_SIZE) {
            return EMPTY;
        }

        String queryStringName = queryStringSchemas[QUERY_STRING_SCHEMA_NAME_INDEX];
        String queryStringValue = queryStringSchemas[QUERY_STRING_SCHEMA_VALUE_INDEX];

        return new HttpRequestParam(queryStringName, queryStringValue);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpRequestParam that = (HttpRequestParam) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public boolean isNotEmpty() {
        return !this.equals(EMPTY);
    }
}
