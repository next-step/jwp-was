package webserver.http.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatus;
import webserver.http.Protocol;

import java.util.Objects;

public class ResponseLine {

    private static final Logger logger = LoggerFactory.getLogger(ResponseLine.class);
    public static final String PROTOCOL_SEPARATOR = " ";
    public static final String VALUE_SEPARATOR = " ";

    private final Protocol protocol;
    private final HttpStatus httpStatus;

    public ResponseLine(ResponseLine responseLine) {
        this.protocol = responseLine.protocol;
        this.httpStatus = responseLine.httpStatus;
    }

    public ResponseLine(String protocol, HttpStatus httpStatus) {
        this.protocol = new Protocol(protocol);
        this.httpStatus = httpStatus;
    }

    public ResponseLine(Protocol protocol, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
    }

    public ResponseLine(String value) {
        this(makeResponseLine(value));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getProtocolAndVersion() {
        return protocol.getProtocolAndVersion();
    }

    public String getResponseLine() {
        StringBuffer sb = new StringBuffer();

        sb.append(getProtocolAndVersion())
                .append(PROTOCOL_SEPARATOR)
                .append(getHttpStatus().getCode())
                .append(PROTOCOL_SEPARATOR)
                .append(getHttpStatus().getMessage())
                .append(System.lineSeparator());

        return sb.toString();
    }

    private static ResponseLine makeResponseLine(String value) {
        String[] values = value.split(VALUE_SEPARATOR);
        return new ResponseLine(new Protocol(values[0]), HttpStatus.valueOf(values[1]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseLine that = (ResponseLine) o;
        return Objects.equals(protocol, that.protocol) && httpStatus == that.httpStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, httpStatus);
    }

    @Override
    public String toString() {
        return "ResponseLine{" +
                "protocol=" + protocol +
                ", httpStatus=" + httpStatus +
                '}';
    }
}
