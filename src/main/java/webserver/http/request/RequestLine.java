package webserver.http.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class RequestLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestLine.class);
    private static final String DELIMITER = " ";
    private static final String VERSION_DELIMITER = "/";

    private Method method;

    private Uri uri;

    private Protocol protocol;

    private Version version;

    private RequestLine(Method method, Uri uri, Protocol protocol, Version version) {
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine parse(String readLine) {
        logger.debug("request line : {}", readLine);
        if (!StringUtils.hasText(readLine)) {
            throw new IllegalArgumentException("요청 정보가 올바르지 않습니다.");
        }
        String[] tokens = readLine.split(DELIMITER);

        Method method = Method.valueOf(tokens[0]);
        Uri uri = Uri.valueOf(tokens[1]);
        Protocol protocol = Protocol.valueOf(tokens[2].split(VERSION_DELIMITER)[0]);
        Version version = Version.of(tokens[2].split(VERSION_DELIMITER)[1]);

        return new RequestLine(method, uri, protocol, version);
    }

    public Method getMethod() {
        return method;
    }

    public Uri getUri() {
        return uri;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public Version getVersion() {
        return version;
    }
}
