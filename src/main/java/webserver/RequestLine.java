package webserver;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class RequestLine {

    private static final String BLANK = " ";

    private String method;
    private String path;
    private Protocol protocol;

    public RequestLine(String requestLine) {
        String[] split = requestLine.split(BLANK);
        this.method = split[0];
        this.path = split[1];

        String protocolNameAndVersion = split[2];
        this.protocol = new Protocol(protocolNameAndVersion);
    }
}
