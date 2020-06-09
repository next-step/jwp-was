package http;

import static http.HeaderName.CONTENT_LENGTH;
import static http.HeaderName.CONTENT_TYPE;
import static http.HeaderName.REQUEST_COOKIE;

import http.session.HttpSessionStorage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;
import utils.HttpUtils;
import utils.StringUtils;

public class Headers {

    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private final Map<String, String> headers;
    private final Cookie cookie;

    private Headers(Map<String, String> headers, Cookie cookie) {
        this.headers = new HashMap<>(headers);
        this.cookie = cookie;
    }

    public static Headers from(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();
        for (String line : headerLines) {
            headers.putAll(HttpUtils.getPair(line, ":"));
        }
        Cookie cookie = Cookie.from(headers.getOrDefault(REQUEST_COOKIE.getKey(), ""));
        return new Headers(headers, cookie);
    }

    public static Headers newInstance(){
        return from(Arrays.asList());
    }

    public int getContentLength() {
        String length = this.headers.getOrDefault(CONTENT_LENGTH.getKey(), "0");
        return Integer.parseInt(length);
    }

    public String getCookie(String key) {
        return this.cookie.get(key);
    }

    public void addCookie(String name, String value) {
        this.cookie.put(name, value);
    }

    public Parameters parseBody(String body) {
        if (StringUtils.isEmpty(body)) {
            return Parameters.empty();
        }

        String contentType = this.headers.getOrDefault(CONTENT_TYPE.getKey(), "");

        if (contentType.equals(APPLICATION_FORM_URLENCODED)) {
            return parseForFormUrlEncoded(body);
        }

        return Parameters.empty();
    }

    public void response(OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        for (Entry<String, String> entry : this.headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            dos.writeBytes(key + ": " + value + "\r\n");
        }

        this.cookie.response(out);
    }

    public HttpSession getHttpSession(HttpSessionStorage httpSessionStorage) {
        return this.cookie.getHttpSession(httpSessionStorage);
    }

    private Parameters parseForFormUrlEncoded(String body) {
        return new Parameters(HttpUtils.getPairs(body, "&", "="));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Headers headers1 = (Headers) o;
        return Objects.equals(headers, headers1.headers) &&
            Objects.equals(cookie, headers1.cookie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, cookie);
    }


}
