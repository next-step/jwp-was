package webserver.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import webserver.Protocol;

@EqualsAndHashCode
@AllArgsConstructor
public class ResponseLine {

    private static final String PROTOCOL = "HTTP/1.1";

    private Protocol protocol;
    private String code;
    private String statusCodeMessage;

    public static ResponseLine of(String code, String statusCodeMessage) {
        return new ResponseLine(Protocol.of(PROTOCOL), code, statusCodeMessage);
    }

    public String response() {
        return protocol.getNameAndVersion() + " " + code + " " + statusCodeMessage + "\r\n";
    }
}
