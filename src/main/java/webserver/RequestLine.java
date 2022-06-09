package webserver;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestLine {
    private String[] values;

    private String[] protocol;

    public RequestLine(final String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        String[] values = text.split(" ");

        if (values.length != 3) {
            throw new IllegalArgumentException();
        }

        String[] protocol = values[2].split("/");
        if (protocol.length != 2) {
            throw new IllegalArgumentException();
        }

        this.values = values;
        this.protocol = protocol;
    }

    public HttpMethod getMethod() {
        return HttpMethod.valueOf(values[0]);
    }

    public String getPath() {
        return values[1];
    }

    public String getProtocol() {
        return protocol[0];
    }

    public String getProtocolVersion() {
        return protocol[1];
    }

    public Map<String, String> getParameters() {
        String[] query = values[1].split("\\?");
        String[] params = query[1].split("&");
        return Arrays.stream(params)
                .map(param -> param.split("="))
                .collect(Collectors.toMap(values -> values[0], values -> values[1]));
    }
}
