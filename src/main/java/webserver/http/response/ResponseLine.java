package webserver.http.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import webserver.Protocol;

import java.io.DataOutputStream;
import java.io.IOException;

@EqualsAndHashCode
@AllArgsConstructor
public class ResponseLine {

    private static final String PROTOCOL = "HTTP/1.1";

    private Protocol protocol;
    private String code;
    private String statusCodeMessage;

    public ResponseLine(Protocol protocol, HttpResponseStatus status) {
        this.protocol = protocol;
        this.code = status.getCode();
        this.statusCodeMessage = status.getDescription();
    }

    public static ResponseLine of(HttpResponseStatus status) {
        Protocol protocol = Protocol.of(PROTOCOL);
        return new ResponseLine(protocol, status);
    }

    public void response(DataOutputStream dos) throws IOException {
        dos.writeBytes(response());
    }

    public String response() {
        return protocol.getNameAndVersion() + " " + code + " " + statusCodeMessage + "\r\n";
    }
}
