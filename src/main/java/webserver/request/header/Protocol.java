package webserver.request.header;

import java.util.Arrays;

import webserver.request.header.exception.InvalidProtocolException;

public enum Protocol {
    HTTP("HTTP");

    private final String name;

    Protocol(String name) {
        this.name = name;
    }

    static Protocol from(String name) {
        return Arrays.stream(values())
                .filter(protocol -> protocol.name.equals(name))
                .findAny()
                .orElseThrow(() -> new InvalidProtocolException("유효하지 않은 프로토콜 입니다."));
    }
}
