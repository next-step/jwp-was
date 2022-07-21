package webserver.http.request.parser;

import webserver.http.request.RequestLine;

public class RequestLineParser {
    private static final String REQUEST_LINE_DELIMITER = " ";

    private static final int REQUEST_LINE_SPLIT_SIZE = 3;

    private final MethodParser methodParser;
    private final URIParser uriParser;
    private final ProtocolParser protocolParser;

    public RequestLineParser(MethodParser methodParser, URIParser uriParser, ProtocolParser protocolParser) {
        this.methodParser = methodParser;
        this.uriParser = uriParser;
        this.protocolParser = protocolParser;
    }

    public RequestLine parse(String message) {
        String[] splitMessage = message.split(REQUEST_LINE_DELIMITER);
        if (splitMessage.length != REQUEST_LINE_SPLIT_SIZE) {
            throw new RuntimeException(String.format("'[Method] [URI] [Protocol]' 형식의 requestLine 메시지가 아닙니다. {message=%s}", message));
        }

        String method = splitMessage[0];
        String uri = splitMessage[1];
        String protocol = splitMessage[2];

        return new RequestLine(
                methodParser.parse(method),
                uriParser.parse(uri),
                protocolParser.parse(protocol)
        );
    }
}
