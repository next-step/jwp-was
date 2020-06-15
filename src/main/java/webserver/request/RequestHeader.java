package webserver.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class RequestHeader {

    private static final String REQUEST_HEADER_SEPARATOR = ": ";

    private String name;
    private String value;

    public static RequestHeader of(String requestHeaderText) {
        String[] split = requestHeaderText.split(REQUEST_HEADER_SEPARATOR, 2);
        return new RequestHeader(split[0], split[1]);
    }

}
