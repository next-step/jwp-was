package types;

import exception.ProtocolNotFoundException;

import java.util.Arrays;

public enum Protocol {

    HTTP;

    public static Protocol find(String requestProtocol) {
        return Arrays.stream(Protocol.values())
                .filter(protocol -> requestProtocol.toUpperCase().equals(protocol.name()))
                .findAny()
                .orElseThrow(ProtocolNotFoundException::new);
    }
}
