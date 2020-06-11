package http.request;

import java.util.Map;

public class RequestLineParser {

    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String SEPARATOR = " ";
    private static final String SEPARATOR_URI = "\\?";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;
    private static final int MIN_QUERY_STRING_SIZE = 1;

    public static RequestLine parse(final String line) {
        String[] lines = line.split(SEPARATOR);
        String method = lines[METHOD_INDEX];
        String path = lines[PATH_INDEX];
        String protocol = lines[PROTOCOL_INDEX];

        if (method.equals(GET_METHOD)) {
            return new RequestLine(createMethodGetByPath(path), new Protocol(protocol));
        }

        return new RequestLine(new RequestMethodPost(path), new Protocol(protocol));
    }

    public static RequestLine parse(final String line, String requestBody) {
        String[] lines = line.split(SEPARATOR);
        String method = lines[METHOD_INDEX];
        String path = lines[PATH_INDEX];
        String protocol = lines[PROTOCOL_INDEX];
        if (method.equals(POST_METHOD)) {
            return new RequestLine(createMethodPostByRequestBody(path, requestBody), new Protocol(protocol));
        }
        throw new IllegalArgumentException("잘 못된 요청입니다.");
    }


    private static RequestMethod createMethodPostByRequestBody(String path, String requestBody) {
        RequestParameters requestParameters = new RequestParameters(requestBody);
        String[] queryString = path.split(SEPARATOR_URI);
        if (queryString.length > MIN_QUERY_STRING_SIZE) {
            RequestParameters requestQueryString = new RequestParameters(queryString[1]);
            Map<String, String> parameters = requestQueryString.getRequestParameters();
            parameters.putAll(requestParameters.getRequestParameters());
            return new RequestMethodPost(queryString[0], new RequestParameters(parameters));
        }

        return new RequestMethodPost(path, requestParameters);
    }

    private static RequestMethod createMethodGetByPath(String path) {
        String[] queryString = path.split(SEPARATOR_URI);

        if (queryString.length > MIN_QUERY_STRING_SIZE) {
            return new RequestMethodGet(queryString[0], new RequestParameters(queryString[1]));
        }
        return new RequestMethodGet(path);
    }
}
