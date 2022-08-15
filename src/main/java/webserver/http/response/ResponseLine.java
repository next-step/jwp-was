package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatus;
import webserver.http.Protocol;

public class ResponseLine {

    private static final Logger logger = LoggerFactory.getLogger(ResponseLine.class);

    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public ResponseLine(String protocol, HttpStatus httpStatus) {
        this.protocol = new Protocol(protocol);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getProtocolAndVersion() {
        logger.debug("Protocol : {}", protocol.getProtocolAndVersion());
        return protocol.getProtocolAndVersion();
    }
}
