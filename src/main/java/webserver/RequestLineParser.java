package webserver;

import webserver.domain.*;

public class RequestLineParser {
    private static final String REQUEST_LINE_DELIMITER = " ";
    private static final String PROTOCOL_DELIMITER = "/";

    public static RequestLine parseRequestLine(String startLine){
        String[] httpElement = startLine.split(REQUEST_LINE_DELIMITER);

        HttpMethod httpMethod = HttpMethod.valueOf(httpElement[RequestLine.METHOD_INDEX]);
        Path path = new Path(httpElement[RequestLine.PATH_INDEX]);
        ProtocolVersion protocol = parseProtocolVersion(httpElement[RequestLine.PROTOCOL_INDEX]);

        return new RequestLine(httpMethod, path, protocol);
    }

    private static ProtocolVersion parseProtocolVersion(final String protocolVersion){
        String[] splitProtocolVersion = protocolVersion.split(PROTOCOL_DELIMITER);

        System.out.println(splitProtocolVersion[ProtocolVersion.VERSION_INDEX]);

        return new ProtocolVersion(
                Protocol.valueOf(splitProtocolVersion[ProtocolVersion.PROTOCOL_INDEX]),
                HttpVersion.from(splitProtocolVersion[ProtocolVersion.VERSION_INDEX])
        );
    }
}
