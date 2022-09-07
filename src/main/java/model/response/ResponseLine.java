package model.response;

import webserver.Protocol;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseLine {
    public static final String EMPTY_SPACE = " ";
    private Protocol protocol;
    private String status;
    private String message;

    public ResponseLine(Protocol protocol, String status, String message) {
        this.protocol = protocol;
        this.status = status;
        this.message = message;
    }

    public static ResponseLine redirect() {
        return new ResponseLine(new Protocol("HTTP/1.1"), "302", "OK");
    }

    public static ResponseLine success() {
        return new ResponseLine(new Protocol("HTTP/1.1"), "200", "OK");
    }

    public String getInfo() {
        return getProtocolInfo() + EMPTY_SPACE + status + EMPTY_SPACE + message;
    }

    private String getProtocolInfo() {
        return protocol.getProtocol() + "/" + protocol.getVersion();
    }

    @Override
    public String toString() {
        return "ResponseLine{" +
                "protocol=" + protocol +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public void writeOutput(DataOutputStream dos) throws IOException {
        dos.writeBytes(getInfo() + "\n");
    }
}
