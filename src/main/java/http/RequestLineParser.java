package http;

import java.util.HashMap;
import java.util.Map;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        String values[] = requestLine.split(" ");
        Protocol protocol = new Protocol(values[2]);
        Map<String, Object> queryString = new HashMap<>();
        // queryString parsing 로직 구현

        return new RequestLine(values[0], values[1], protocol.getProtocol(), protocol.getVersion(), queryString);
    }
}
