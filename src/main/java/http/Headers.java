package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import utils.HttpUtils;
import utils.StringUtils;

public class Headers {

    private final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private Map<String, String> headers;

    public Headers() {
        this(new HashMap<>());
    }

    public Headers(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    public static Headers from(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();
        for (String line : headerLines) {
            headers.putAll(HttpUtils.getPair(line, ":"));
        }
        return new Headers(headers);
    }

    public void add(String name, String value) {
        this.headers.put(name, value);
    }

    public int getContentLength() {
        String length = this.headers.getOrDefault(HeaderName.CONTENT_LENGTH.name, "0");
        return Integer.parseInt(length);
    }

    public Map<String, String> getCookies() {
        String cookies = this.headers.getOrDefault("Cookie", "");
        return Cookie.from(cookies).getCookies();
    }

    public Parameters parseBody(String body) {
        if (StringUtils.isEmpty(body)) {
            return Parameters.empty();
        }

        String contentType = this.headers.getOrDefault(HeaderName.CONTENT_TYPE.name, "");

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
        return
            Objects.equals(APPLICATION_FORM_URLENCODED, headers1.APPLICATION_FORM_URLENCODED)
                &&
                Objects.equals(headers, headers1.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(APPLICATION_FORM_URLENCODED, headers);
    }


    private enum HeaderName {
        CONTENT_LENGTH("Content-Length"),
        CONTENT_TYPE("Content-Type");

        private final String name;

        HeaderName(String name) {
            this.name = name;
        }
    }
}
