package webserver.http.view.request;

import webserver.http.domain.KeyValuePair;
import webserver.http.domain.request.URI;
import webserver.http.view.KeyValuePairParser;

public class URIParser {
    private static final String QUERY_PARAMETER_DELIMITER = "?";
    
    private final KeyValuePairParser keyValuePairParser;
    private final ParametersParser queryParametersParser;

    public URIParser(KeyValuePairParser keyValuePairParser, ParametersParser queryParametersParser) {
        this.keyValuePairParser = keyValuePairParser;
        this.queryParametersParser = queryParametersParser;
    }

    public URI parse(String message) {
        KeyValuePair<String, String> pathQueryParamPair = keyValuePairParser.parse(message, QUERY_PARAMETER_DELIMITER, false);
        String path = pathQueryParamPair.getKey();
        String queryParameters = pathQueryParamPair.getValue();
        return new URI(path, queryParametersParser.parse(queryParameters));
    }
}
