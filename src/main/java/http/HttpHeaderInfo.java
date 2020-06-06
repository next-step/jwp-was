package http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class HttpHeaderInfo {
    private Map<String, String> headerInfoMap;

    public HttpHeaderInfo() {
        this.headerInfoMap = new HashMap<>();
    }

    public void addHeaderValue(String headerLine) {
        String[] values = headerLine.split(":",2);

        if(values.length < 2) {
            throw new IllegalArgumentException();
        }

        this.headerInfoMap.put(values[0].trim(), values[1].trim());
    }

    public void addKeyAndValue(String key, String value) {
        if(this.headerInfoMap == null) this.headerInfoMap = new HashMap<>();

        headerInfoMap.put(key,value);
    }

    public String getValue(String headerName) {
        return this.headerInfoMap.getOrDefault(headerName, StringUtils.EMPTY);
    }

    public String makeResponseHeader() {
        String responseHeader = "";

        for (String key : this.headerInfoMap.keySet()) {
            responseHeader = responseHeader + key + ": " + headerInfoMap.get(key) + System.lineSeparator();
        }
        responseHeader = responseHeader + System.lineSeparator();

        return responseHeader;
    }

}
