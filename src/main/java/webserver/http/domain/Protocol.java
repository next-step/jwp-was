package webserver.http.domain;

import webserver.http.domain.exception.BadRequestException;

import java.util.Arrays;
import java.util.Objects;

public enum Protocol {
    HTTP_1_1("HTTP", "1.1");

    private static final String PROTOCOL_DELIMITER_REGEX = "/";

    private static final int PROTOCOL_SPLIT_SIZE = 2;


    private final String type;
    private final String version;

    Protocol(String type, String version) {
        this.type = type;
        this.version = version;
    }

    public static Protocol from(String message) {
        if (Objects.isNull(message) || message.isBlank()) {
            throw new BadRequestException("프로토콜은 빈값 혹은 null이 될 수 없습니다.");
        }
        String[] splitProtocol = message.split(PROTOCOL_DELIMITER_REGEX);
        if (splitProtocol.length != PROTOCOL_SPLIT_SIZE) {
            throw new BadRequestException("지원하지 않는 프로토콜 방식입니다.");
        }

        String type = splitProtocol[0];
        String version = splitProtocol[1];

        return Arrays.stream(values())
                .filter(protocol -> protocol.type.equals(type) &&
                        protocol.version.equals(version))
                .findAny()
                .orElseThrow(() -> new BadRequestException("지원하지 않는 프로토콜 방식입니다."));
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
