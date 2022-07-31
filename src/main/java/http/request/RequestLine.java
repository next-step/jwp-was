package http.request;

import java.util.Map;
import java.util.regex.Pattern;

import http.exception.NotFoundExtensionException;

public class RequestLine {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String QUERY_PARAMETER_DELIMITER = "\\?";

    private static final int ONLY_PATH = 1;
    private static final Pattern REGEX = Pattern.compile("([/.\\w]+)([.][\\w]+)([?][\\w./=]+)?");

    private final HttpMethod httpMethod;
    private final String url;
    private final Protocol protocol;
    private final QueryParams queryParams;

    public RequestLine(String line) {
        var specs = line.split(REQUEST_LINE_DELIMITER);

        this.httpMethod = HttpMethod.valueOf(specs[0]);
        this.protocol = Protocol.of(specs[2]);

        var pathWithParameters = specs[1]
            .split(QUERY_PARAMETER_DELIMITER);
        this.url = pathWithParameters[0];
        this.queryParams = parseParameters(pathWithParameters);
    }

    private QueryParams parseParameters(String[] pathWithParameters) {
        if (pathWithParameters.length == ONLY_PATH) {
            return QueryParams.EMPTY;
        }
        return QueryParams.of(pathWithParameters[1]);
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol.getProtocolType()
            .name();
    }

    public String getVersion() {
        return protocol.getVersion();
    }

    public Map<String, String> getQueryParams() {
        return queryParams.get();
    }

    public boolean isStaticFile() {
        String lastPath = url.substring(url.lastIndexOf("/"));

        return REGEX.matcher(lastPath)
            .find();
    }

    public String getFileExtension() {
        if (!isStaticFile()) {
            throw new NotFoundExtensionException(getUrl());
        }

        var splitedUrl = url.split("\\.");
        return splitedUrl[splitedUrl.length - 1];
    }
}
