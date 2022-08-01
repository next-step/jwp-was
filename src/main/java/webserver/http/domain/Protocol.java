package webserver.http.domain;

import webserver.http.domain.exception.BadRequestException;

public class Protocol {
    private static final String PROTOCOL_DELIMITER_REGEX = "/";

    private static final int PROTOCOL_SPLIT_SIZE = 2;

    private final String type;
    private final String version;

    public Protocol(String type, String version) {
        this.type = type;
        this.version = version;
    }
    
    public static Protocol http1Point1() {
        return new Protocol("HTTP", "1.1");
    }

    public static Protocol from(String message) {
        String[] splitProtocol = message.split(PROTOCOL_DELIMITER_REGEX);
        if (splitProtocol.length != PROTOCOL_SPLIT_SIZE) {
            throw new BadRequestException(String.format("'[타입]/[버전]' 형식의 HTTP 프로토콜 메시지가 아닙니다. {message=%s}", message));
        }

        String type = splitProtocol[0];
        String version = splitProtocol[1];
        return new Protocol(type, version);
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Protocol{" +
                "type='" + type + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
