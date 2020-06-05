package http.requests;

import utils.HttpHeader;

import java.util.Map;

// NOTE: Content-Type처럼 규격대로만 날리면 얼마나 좋겠냐만... content-type으로 날리는 클라이언트들도 있단 말이지..
public class HttpRequestHeader {

    private final Map<String, String> headers;

    public HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(HttpHeader httpHeader) {
        return headers.get(httpHeader.getOriginName());
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
