package webserver.http.protocol.parser;

import webserver.http.protocol.Protocol;

public class ProtocolParser {

    private static final String PROTOCOL_DELIMITER_REGEX = "/";
    private static final int PROTOCOL_SPLIT_SIZE = 2;

    public Protocol parse(String message) {
        String[] splitProtocol = message.split(PROTOCOL_DELIMITER_REGEX);
        if (splitProtocol.length != PROTOCOL_SPLIT_SIZE) {
            throw new RuntimeException(String.format("'[타입]/[버전]' 형식의 HTTP 프로토콜 메시지가 아닙니다. {message=%s}", message));
        }

        String type = splitProtocol[0];
        String version = splitProtocol[1];
        return new Protocol(type, version);
    }
}
