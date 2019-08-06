package webserver.http;

import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public class DispatcherSupport {

    String getStatusLine(HttpStatusCode httpStatusCode) {
        return "HTTP/1.1 " + httpStatusCode.getCode() + " " + httpStatusCode.getMessage() + " \r\n";
    }

    String getLocationLine(String location) {
        return "Location: " + location + "\r\n";
    }

    String getContentTypeLine(String contentType) {
        return "Content-Type: " + contentType + ";charset=utf-8\r\n";
    }

    String getContentLengthLine(int lengthOfBodyContent) {
        if (lengthOfBodyContent > 0) {
            return "Content-Length: " + lengthOfBodyContent + "\r\n";
        }

        return "";
    }

    String getSetCookieLine(Map<String, String> cookies) {
        return ofNullable(cookies)
                .map(Map::entrySet)
                .map(entries -> entries.stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .collect(joining(",")))
                .map(cookie -> "Set-Cookie: " + cookie + "; Path=/\r\n")
                .orElse("");
    }

}
