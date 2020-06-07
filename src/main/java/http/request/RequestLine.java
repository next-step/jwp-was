package http.request;

import lombok.Getter;

import java.io.UnsupportedEncodingException;

@Getter
public class RequestLine {
    private static final String REQUEST_LINE_REGEX = " ";
    private static final String PATH_REGEX = "\\?";
    private static final String STATIC_PATH = "./static";
    private static final String TEMPLATES_PATH = "./templates";
    private static final String HTML_EXTENSION = "html";
    private static final String FAVICON_EXTENSION = "ico";

    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private QueryString queryString;

    private RequestLine(HttpMethod httpMethod, String path, Protocol protocol, QueryString queryString) {
        this.method = httpMethod;
        this.path = path;
        this.protocol = protocol;
        this.queryString = queryString;
    }

    private RequestLine(HttpMethod httpMethod, String path, Protocol protocol) throws UnsupportedEncodingException {
        this(httpMethod, path, protocol, QueryString.getInstance(""));
    }

    public static RequestLine getInstance(String requestLine) throws UnsupportedEncodingException {
        String[] values = requestLine.split(" ");
        String[] pathValues = values[1].split(PATH_REGEX);
        Protocol protocol = Protocol.getInstance(values[2]);
        HttpMethod method = HttpMethod.valueOf(values[0]);

        if (method == HttpMethod.GET && pathValues.length > 1) {
            return new RequestLine(method, pathValues[0], protocol, QueryString.getInstance(pathValues[1]));
        }

        return new RequestLine(method, pathValues[0], protocol);
    }

    public String getFilePath() {
        String[] paths = this.path.split("/");
        String fileName = paths[paths.length - 1];
        String[] fileNames = fileName.split("\\.");

        String extension = fileNames[1];
        if (extension.equals(HTML_EXTENSION) || extension.equals(FAVICON_EXTENSION)) {
            return TEMPLATES_PATH + this.path;
        }
        return STATIC_PATH + this.path;
    }

    public String getParameter(String key) {
        return this.queryString.getValue(key);
    }
}
