package http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import utils.StringUtils;

public class Headers {

    private final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private Map<String, String> headers;

    public Headers(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    public static Headers from(List<String> headerLines) {
        Map<String, String> headers = new HashMap<>();

        for (String line : headerLines) {
            if (StringUtils.isEmpty(line)) {
                continue;
            }
            String[] values = line.split(":");
            if (values.length != 2) {
                continue;
            }

            headers.put(values[0].trim(), values[1].trim());
        }
        return new Headers(headers);
    }

    public int getContentLength() {
        String length = this.headers.getOrDefault(HeaderName.CONTENT_LENGTH.name, "0");
        return Integer.parseInt(length);
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

    public List<String> toLines(){
        return this.headers.keySet().stream()
            .map(name -> name + ": " + headers.get(name))
            .collect(Collectors.toList());
    }

    private Parameters parseForFormUrlEncoded(String body) {
        Map<String, String> map = new HashMap<>();
        String[] parameters = body.split("&");
        for (String parameter : parameters) {
            String values[] = parameter.split("=");
            try {
                map.put(URLDecoder.decode(values[0].trim(), "UTF-8"), URLDecoder.decode(values[1].trim(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return new Parameters(map);
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
