package http;

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
        return new RequestLine(createMethod(method, path), new Protocol(protocol));
    }

    private static RequestMethod createMethod(String method, String path) {
        if (method.equals(GET_METHOD)) {
            return createMethodGetByPath(path);
        }

        if (method.equals(POST_METHOD)) {
            return new RequestMethodPost(path);
        }

        throw new IllegalArgumentException("잘 못된 요청 입니다.");
    }

    private static RequestMethod createMethodGetByPath(String path) {
        String[] queryString = path.split(SEPARATOR_URI);

        if (queryString.length > MIN_QUERY_STRING_SIZE) {
            QueryStringsTranslator queryStringsTranslator = new DefaultQueryStringsTranslator(queryString[1]);
            return new RequestMethodGet(path, new QueryStrings(queryStringsTranslator.create()));
        }
        return new RequestMethodGet(path);
    }
}
