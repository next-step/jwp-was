package webserver.http.view.request;

import webserver.http.view.ProtocolParser;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.RequestLine;

public class RequestLineParser {
    private static final String REQUEST_LINE_DELIMITER_REGEX = " ";

    private static final int REQUEST_LINE_SPLIT_SIZE = 3;

    private final URIParser uriParser;
    private final ProtocolParser protocolParser;

    public RequestLineParser(URIParser uriParser, ProtocolParser protocolParser) {
        this.uriParser = uriParser;
        this.protocolParser = protocolParser;
    }

    public RequestLine parse(String message) {
        String[] splitMessage = message.split(REQUEST_LINE_DELIMITER_REGEX);
        if (splitMessage.length != REQUEST_LINE_SPLIT_SIZE) {
            throw new RuntimeException(String.format("'[Method] [URI] [Protocol]' 형식의 requestLine 메시지가 아닙니다. {message=%s}", message));
        }

        String method = splitMessage[0];
        String uri = splitMessage[1];
        String protocol = splitMessage[2];

        return new RequestLine(
                Method.from(method),
                uriParser.parse(uri),
                protocolParser.parse(protocol)
        );
    }
}
