package http.request.requestline;

import http.request.method.HttpMethod;
import http.request.requestline.exception.RequestLineParsingException;
import http.request.requestline.path.Path;
import http.request.requestline.protocol.ProtocolSpec;
import http.request.requestline.protocol.ProtocolSpecPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestLineParser {

    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final int TOKEN_SIZE = 3;

    public static RequestLine parse(String request) {
        if (StringUtils.isEmpty(request)) {
            throw new RequestLineParsingException("Parameter for creating RequestLine is Empty.");
        }

        String[] tokens = splitRequestLine(request);

        HttpMethod method = HttpMethod.valueOf(tokens[0]);
        Path path = new Path(tokens[1]);
        ProtocolSpec protocolSpec = ProtocolSpecPool.find(tokens[2]);

        return RequestLine.builder()
                .method(method)
                .path(path)
                .protocolSpec(protocolSpec)
                .build();
    }

    private static String[] splitRequestLine(String request) {
        String[] tokens = request.trim().split(REQUEST_LINE_DELIMITER);

        if (tokens.length != TOKEN_SIZE) {
            throw new RequestLineParsingException();
        }
        return tokens;
    }
}
