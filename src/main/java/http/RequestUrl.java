package http;

import java.util.Objects;
import utils.StringUtils;

public class RequestUrl {

    private static final String SPLITTER = "\\?";

    private final String path;
    private final QueryString queryString;

    private RequestUrl(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static RequestUrl of(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("empty value");
        }

        String[] values = value.split(SPLITTER);
        if (values.length == 2) {
            return of(values[0], values[1]);
        }

        return of(values[0], "");
    }

    public static RequestUrl of(String path, String queryString) {
        return new RequestUrl(path, QueryString.of(queryString));
    }

    public Parameters getParameters() {
        return new Parameters(this.queryString.getAll());
    }

    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestUrl that = (RequestUrl) o;
        return Objects.equals(path, that.path) &&
            Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryString);
    }

}
