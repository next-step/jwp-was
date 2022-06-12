package webserver.http;

import org.springframework.util.StringUtils;

public class Uri {
    private static final String QUERY_STRING_DELIMITER = "\\?";

    private String path;

    private String queryString;

    private Uri(String path, String queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Uri valueOf(String token) {
        if (!StringUtils.hasText(token)) {
            throw new IllegalArgumentException("Uri 정보가 올바르지 않습니다.");
        }

        String[] values = token.split(QUERY_STRING_DELIMITER);
        if (values.length > 1) {
            return new Uri(values[0], values[1]);
        }

        return new Uri(token, null);
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}
