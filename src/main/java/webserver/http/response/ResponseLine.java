package webserver.http.response;

import lombok.EqualsAndHashCode;
import webserver.Protocol;

@EqualsAndHashCode
public class ResponseLine {

    private static final String PROTOCOL = "HTTP/1.1";

    private Protocol protocol = Protocol.of(PROTOCOL);
    private String code;
    private String statusCodeMessage;

    public String response() {
        return protocol.getNameAndVersion() + " " + code + " " + statusCodeMessage + "\r\n";
    }

    public void setStatus(HttpResponseStatus status) {
        this.code = status.getCode();
        this.statusCodeMessage = status.getDescription();
    }
}
