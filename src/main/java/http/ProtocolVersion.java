package http;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Args;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ProtocolVersion {

    private static final String REQUEST_PROTOCOL_VERSION_FORMAT = "(?<protocol>.+)/(?<version>\\d.\\d)";
    private static final Pattern REQUEST_PROTOCOL_VERSION_PATTERN = Pattern.compile(REQUEST_PROTOCOL_VERSION_FORMAT);
    protected static final String ILLEGAL_PROTOCOL_VERSION = "유효하지 않은 프로토콜/버전 입니다.";

    private final String httpProtocol;
    private final String version;

    public ProtocolVersion(String protocolAndVersion) {
        Matcher protocolVersionMatcher = Args.checkPattern(REQUEST_PROTOCOL_VERSION_PATTERN.matcher(protocolAndVersion),
            ILLEGAL_PROTOCOL_VERSION);

        this.httpProtocol = protocolVersionMatcher.group("protocol");
        this.version = protocolVersionMatcher.group("version");
    }

    public String getHttpProtocol() {
        return this.httpProtocol;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProtocolVersion)) {
            return false;
        }

        ProtocolVersion protocolVersion1 = (ProtocolVersion) o;

        if (!Objects.equals(httpProtocol, protocolVersion1.httpProtocol)) {
            return false;
        }
        return Objects.equals(version, protocolVersion1.version);
    }

    @Override
    public int hashCode() {
        int result = httpProtocol != null ? httpProtocol.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return httpProtocol + "/" + version;
    }
}
