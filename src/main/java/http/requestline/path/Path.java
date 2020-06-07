package http.requestline.path;

import http.querystring.QueryString;
import http.requestline.exception.RequestLineParsingException;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class Path {

    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int QUERY_STRING_EXISTENCE_SIZE = 2;

    private final Uri uri;
    private final QueryString queryString;

    public Path(String uriPath) {
        if (StringUtils.isEmpty(uriPath)) {
            throw new RequestLineParsingException("RequestLine Path is empty.");
        }

        String[] tokens = uriPath.split(QUERY_STRING_DELIMITER);
        if (tokens.length > QUERY_STRING_EXISTENCE_SIZE) {
            throw new RequestLineParsingException("RequestLine Path is Illegal Format.");
        }

        uri = new Uri(tokens[0]);
        String query = extractQuery(tokens);
        queryString = new QueryString(query);
    }

    private String extractQuery(String[] tokens) {
        return existsQueryString(tokens) ? tokens[1] : "";
    }

    private boolean existsQueryString(String[] tokens) {
        return tokens.length == QUERY_STRING_EXISTENCE_SIZE;
    }
}
