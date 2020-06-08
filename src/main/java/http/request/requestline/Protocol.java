package http.request.requestline;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.exception.BadRequestException;
import lombok.Getter;

import java.util.*;

@Getter
public class Protocol {
    static final String HTTP = "HTTP";
    static final Set<String> ALLOWED_HTTP_VERSION = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("1.0", "1.1")));

    private static final String SPLITTER = "/";

    private final String protocol;
    private final String version;

    public Protocol(String protocol, String version) {
        if (!HTTP.equals(protocol) || !ALLOWED_HTTP_VERSION.contains(version)) {
            throw new BadRequestException();
        }

        this.protocol = protocol;
        this.version = version;
    }

    public static Protocol of(String protocolAndVersion) {
        if (StringUtils.isEmpty(protocolAndVersion)) {
            throw new BadRequestException();
        }

        String[] split = protocolAndVersion.split(SPLITTER, 2);

        if (split.length != 2) {
            throw new BadRequestException();
        }

        return new Protocol(split[0], split[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Protocol protocol1 = (Protocol) o;
        return Objects.equals(protocol, protocol1.protocol) &&
            Objects.equals(version, protocol1.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }

    @Override
    public String toString() {
        return protocol + SPLITTER + version;
    }
}
