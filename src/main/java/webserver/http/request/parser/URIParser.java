package webserver.http.request.parser;

import webserver.http.request.KeyValuePair;
import webserver.http.request.QueryParameters;
import webserver.http.request.URI;

import java.util.HashMap;

public class URIParser {
    private static final String QUERY_PARAMETER_DELIMITER = "?";
    
    private final KeyValuePairParser keyValuePairParser;
    private final QueryParametersParser queryParametersParser;

    public URIParser(KeyValuePairParser keyValuePairParser, QueryParametersParser queryParametersParser) {
        this.keyValuePairParser = keyValuePairParser;
        this.queryParametersParser = queryParametersParser;
    }

    public URI parse(String message) {
        if (!message.contains(QUERY_PARAMETER_DELIMITER)) {
            return new URI(message);
        }

        KeyValuePair pathQueryParamPair = keyValuePairParser.parse(message, QUERY_PARAMETER_DELIMITER);
        String path = pathQueryParamPair.getKey();
        String queryParameters = pathQueryParamPair.getValue();
        return new URI(path, queryParametersParser.parse(queryParameters));
    }
}
