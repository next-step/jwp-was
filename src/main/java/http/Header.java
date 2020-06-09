package http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.Const.HttpConst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class Header {
    private Map<String, String> headers;

    public Header() {
        this.headers = new HashMap<>();
    }

    public void addHeaderValue(String headerLine) {
        String[] values = headerLine.split(HttpConst.HEADER_SEPARATOR,2);

        if(values.length < 2) {
            throw new IllegalArgumentException("Invalid header format");
        }

        this.headers.put(values[0].trim(), values[1].trim());
    }

    public void addKeyAndValue(String key, String value) {
        if(this.headers == null) this.headers = new HashMap<>();

        headers.put(key,value);
    }

    public String getValue(String headerName) {
        return this.headers.getOrDefault(headerName, StringUtils.EMPTY);
    }

    public boolean isContainsKey(String key) {
        return this.headers.containsKey(key);
    }

    public String makeResponseHeader() {
        StringBuilder responseHeader = new StringBuilder();

        for (String key : this.headers.keySet()) {
            responseHeader.append(key).append(HttpConst.HEADER_SEPARATOR).append(headers.get(key)).append(HttpConst.CRLF);
        }
        responseHeader.append(HttpConst.CRLF);

        return responseHeader.toString();
    }

}
