package http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.Const.HttpConst;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class Header {
    private Map<String, String> headers;

    public Header() {
        this.headers = new HashMap<>();
    }

    public void addHeaderValue(String headerLine) {
        String[] values = headerLine.split(HttpConst.HEADER_SEPARATOR, 2);

        if (values.length < 2) {
            throw new IllegalArgumentException("Invalid header format");
        }

        this.headers.put(values[0].trim(), values[1].trim());
    }

    public void addKeyAndValue(String key, String value) {
        if (this.headers == null) this.headers = new HashMap<>();

        headers.put(key, value);
    }

    public String getValue(String headerName) {
        return this.headers.getOrDefault(headerName, StringUtils.EMPTY);
    }

    public boolean isContainsKey(String key) {
        return this.headers.containsKey(key);
    }

    public String makeResponseHeader() {

        return this.headers.keySet()
                .stream()
                .map(key -> String.format("%s=%s",key, headers.get(key)))
                .collect(Collectors.joining(HttpConst.CRLF))
                .concat(HttpConst.CRLF)
                .concat(HttpConst.CRLF);
    }

    public String makeCookieHeader(Cookie cookie) {
        Map<String,String> cookieValues = cookie.getCookieValues();
        return cookieValues
                .keySet()
                .stream()
                .map(key -> String.format("Set-Cookie : %s=%s %s", key, cookieValues.get(key), HttpConst.CRLF))
                .collect(Collectors.joining());
    }
}
