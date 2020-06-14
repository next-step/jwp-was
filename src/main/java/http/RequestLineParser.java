package http;

import java.util.HashMap;
import java.util.Map;

public class RequestLineParser {
    public static RequestLine parse(String requestLine) {
        String values[] = requestLine.split(" ");
        Protocol protocol = new Protocol(values[2]);
        Map<String, Object> queryMap = new HashMap<>();

        String pathAndQueryString = values[1];
        String path = null;
        String queryString = null;
        int delimiterIndex = pathAndQueryString.indexOf("?");
        if(delimiterIndex >= 0) {
            path = pathAndQueryString.substring(0, delimiterIndex + 1);
            queryString = pathAndQueryString.substring(delimiterIndex + 1);

            String parameters[] = queryString.split("&");
            for (String parameter : parameters) {
                String pair[] = parameter.split("=");

                if(pair.length == 2) {
                    queryMap.put(pair[0], pair[1]);
                }
            }
        } else {
            path = pathAndQueryString;
        }
        return new RequestLine(HttpMethod.valueOf(values[0]), path, protocol.getProtocol(), protocol.getVersion(), queryMap);
    }
}