package http;

public class RequestLineParser {

    private static final String SEPARATOR = " ";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    public static RequestLine parse(final String line) {
        String[] lines = line.split(SEPARATOR);
        String method = lines[METHOD_INDEX];
        String path = lines[PATH_INDEX];
        String protocol = lines[PROTOCOL_INDEX];
        return new RequestLine(createMethod(method, path), new Protocol(protocol));
    }

    private static RequestMethod createMethod(String method, String path) {
        if (method.equals("GET")) {
            return new RequestMethodGet(path);
        }
        throw new IllegalArgumentException("잘 못된 요청 입니다.");
    }
}
