package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

public class RequestLine {

    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String QUERY_STRING_DELIMITER = "\\?";

    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private Parameters parameters;

    private RequestLine(HttpMethod method, String path, Parameters parameters, Protocol protocol) {
        this.method = method;
        this.path = path;
        this.parameters = parameters;
        this.protocol = protocol;
    }

    public static RequestLine from(String requestLine) throws IOException {
        logger.info(requestLine);

        String[] tokens = requestLine.trim().split(REQUEST_LINE_DELIMITER);
        String[] pathAndQueryString= tokens[1].split(QUERY_STRING_DELIMITER);

        HttpMethod method = HttpMethod.valueOf(tokens[0]);
        String path = pathAndQueryString[0];
        Parameters parameters = Parameters.emptyParameters();
        Protocol protocol = Protocol.fromString(tokens[2]);

        boolean hasQueryString = pathAndQueryString.length > 1;

        if(hasQueryString) {
            parameters = Parameters.from(URLDecoder.decode(pathAndQueryString[1], HttpRequest.CHAR_SET));
        }

        return new RequestLine(method, path, parameters, protocol);
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return this.protocol.getProtocol();
    }

    public String getProtocolVersion() {
        return this.protocol.getVersion();
    }

    public String getVersion() {
        return this.protocol.getVersion();
    }

    public MultiValueMap<String, String> getParameterMap() {
        return this.parameters;
    }

    public String getParameter(String name) {
        return this.parameters.getParameter(name);
    }

    Parameters getParameters() {
        return parameters;
    }
}
