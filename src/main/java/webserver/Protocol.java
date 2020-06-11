package webserver;

import lombok.EqualsAndHashCode;
import utils.StringUtils;
import webserver.exception.InvalidProtocolFormatException;

import static utils.ValidUtils.assertNotBlank;

@EqualsAndHashCode
public class Protocol {

    private static final String PROTOCOL_SEPARATOR = "/";

    private String name;
    private String version;

    public Protocol(String name, String version) {
        assertNotBlank(name, "프로토콜 이름은 필수입니다");
        assertNotBlank(version, "프로토콜 버전은 필수입니다");

        this.name = name;
        this.version = version;
    }

    public static Protocol of(String protocol) {
        StringUtils.isBlank(protocol);

        String[] nameAndVersion = protocol.split(PROTOCOL_SEPARATOR);
        if (isNonExistent(nameAndVersion)) {
            throw new InvalidProtocolFormatException();
        }

        return new Protocol(nameAndVersion[0], nameAndVersion[1]);
    }

    private static boolean isNonExistent(String[] nameAndVersion) {
        return nameAndVersion.length != 2;
    }
}
