package webserver.response;

import enums.HttpStatusCode;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.JsonUtils;
import webserver.Cookie;
import webserver.HttpHeader;

public class HttpResponse {
    private final HttpStatusCode code;
    private final HttpHeader headers;
    private final byte[] body;
    // 이게 들어오면서 생성자에 문제가 생긴듯....
    private Map<String, Cookie> cookies;

    public HttpResponse(HttpStatusCode code, HttpHeader headers, byte[] body, Map<String, Cookie> cookies) {
        this.code = code;
        this.headers = headers;
        this.body = body;
        this.cookies = cookies;
    }

    public HttpResponse(HttpStatusCode code) throws Exception {
        this(code, new HttpHeader(), new byte[]{}, new HashMap<>());
    }

    public HttpResponse(HttpStatusCode code, byte[] body) {
        this(code, new HttpHeader(), body, new HashMap<>());
    }

    public HttpResponse(HttpStatusCode code, HttpHeader headers) throws Exception {
        this(code, headers, new byte[]{}, new HashMap<>());
    }

    public HttpResponse(HttpStatusCode code, HttpHeader headers, HashMap<String, Cookie> cookies) throws Exception {
        this(code, headers, new byte[]{}, cookies);
    }

    public HttpResponse(HttpStatusCode code, Object body) throws Exception {
        this(code, new HttpHeader(), JsonUtils.convertObjectToJsonString(body).getBytes(StandardCharsets.UTF_8), new HashMap<>());
    }

    public HttpResponse(HttpStatusCode code, HttpHeader headers, Object body) throws Exception {
        this(code, headers, JsonUtils.convertObjectToJsonString(body).getBytes(StandardCharsets.UTF_8), new HashMap<>());
    }

    public HttpStatusCode getStatusCode() {
        return code;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpHeader getHeaders() {
        return headers;
    }

    public Map<String, Cookie> getCookies() {
        return cookies;
    }

    public void addCookie(Cookie cookie) {
        cookies.put(cookie.getKey(), cookie);
    }
}
