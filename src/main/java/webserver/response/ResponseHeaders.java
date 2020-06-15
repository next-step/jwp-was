package webserver.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ResponseHeaders {

    private Map<String, ResponseHeader> responseHeaders;

    public static ResponseHeaders of() {
        Map<String, ResponseHeader> responseHeaders = new HashMap<>();
        return new ResponseHeaders(responseHeaders);
    }

    public void addHeader(String name, String... values) {
        ResponseHeader responseHeader = ResponseHeader.of(name, values);
        responseHeaders.put(name, responseHeader);
    }
}
