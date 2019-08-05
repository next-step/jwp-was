package webserver.response;

import webserver.ModelAndView;
import webserver.StatusCode;
import webserver.request.RequestHolder;

import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static webserver.StatusCode.FOUND;

public class ResponseSupport {

    String getStatusLine(ModelAndView mav) {
        StatusCode statusCode = mav.getStatusCode();
        return "HTTP/1.1 " + statusCode.getCode() + " " + statusCode.getMessage() + " \r\n";
    }

    String getLocationLine(ModelAndView mav) {
        return mav.getStatusCode() == FOUND && mav.getViewName() != null ?
                "Location: " + mav.getViewName() + "\r\n" : "";
    }

    String getContentTypeLine(RequestHolder requestHolder) {
        return "Content-Type: " + requestHolder.getResponseContentType() + ";charset=utf-8\r\n";
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
