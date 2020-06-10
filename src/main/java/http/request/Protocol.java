package http.request;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Protocol {

    private final static String HTTP_PROTOCOL = "HTTP";
    private final static String PROTOCOL_DELIMITER = "/";
    private final static int PROTOCOL_INDEX = 0;
    private final static int VERSION_INDEX = 1;

    private String protocol;
    private String version;

    public Protocol(String protocol, String version) {
        this.protocol = protocol;
        this.version = version;
    }

    public Protocol(String value) {
        String[] values = value.split(PROTOCOL_DELIMITER);
        this.protocol = checkProtocol(values[PROTOCOL_INDEX]);
        this.version = checkVersion(values[VERSION_INDEX]);
    }

    private String checkProtocol(String protocol) {
        if (!HTTP_PROTOCOL.equals(protocol)) {
            throw new IllegalArgumentException("잘못된 프로토콜입니다.");
        }
        return protocol;
    }

    private String checkVersion(String version) {
        Pattern pattern = Pattern.compile("(^[0-9]\\.[0-9]*$)");
        Matcher matcher = pattern.matcher(version);
        if (matcher.find()) {
            return version;
        }
        throw new IllegalArgumentException("잘못된 version 입니다.");
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) &&
                Objects.equals(version, protocol1.version);
    }

}
