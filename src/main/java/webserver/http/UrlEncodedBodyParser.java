package webserver.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class UrlEncodedBodyParser {

    public Map<String, String> parseBody(RequestBody requestBody) {
        return Arrays.stream(requestBody.getBody()
                .split("&"))
                .map(bodyEntry -> bodyEntry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }

}
