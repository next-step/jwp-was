package webserver.http.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import webserver.http.HttpHeader;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class RequestHeader {

    private static final String REQUEST_HEADER_SEPARATOR = ": ";

    private HttpHeader name;
    private String value;

    public static RequestHeader of(String requestHeaderText) {
        String[] split = requestHeaderText.split(REQUEST_HEADER_SEPARATOR, 2);
        return new RequestHeader(HttpHeader.of(split[0]), split[1]);
    }
}
