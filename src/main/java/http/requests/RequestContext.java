package http.requests;

import http.types.HttpMethod;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class RequestContext {

    public static final String REQUEST_HEADER_DELIMITER = ":";
    private static final String KEY_VALUE_DELIMITER = "&";  // 2번째 중복

    private final RequestLine requestLine;
    private final Map<String, String> requestHeaders;
    private final String body;
    private final Map<String, String> formData;

    public RequestContext(String rawRequestLine, List<String> rawRequestHeaders, String body) {
        this.requestLine = new RequestLine(rawRequestLine);
        this.requestHeaders = rawRequestHeaders.stream()
                .map(this::splitRequestHeaderKeyAndValue)
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue)
                );
        this.body = body;
        this.formData = body == null || body.isEmpty() ? new HashMap<>() : parseFormData(body);
    }

    // TODO: <duplicated> Query String parsing과 동일. 이와 같이 key, value로 파싱해야 할 util을 만들자.
    private Map<String, String> parseFormData(String rawFormData) {
        final String decodedPathString = URLDecoder.decode(rawFormData, StandardCharsets.UTF_8);
        return Arrays.stream(decodedPathString.split(KEY_VALUE_DELIMITER))
                .map(this::splitParameter)
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue)
                );
    }

    private AbstractMap.SimpleImmutableEntry<String, String> splitParameter(String raw) {
        final String[] keyAndValue = raw.split("=");
        return new AbstractMap.SimpleImmutableEntry<>(keyAndValue[0], keyAndValue[1]);
    }

    private AbstractMap.SimpleImmutableEntry<String, String> splitRequestHeaderKeyAndValue(String header) {
        final String[] split = header.split(REQUEST_HEADER_DELIMITER);
        return new AbstractMap.SimpleImmutableEntry<>(split[0].trim(), split[1].trim());
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                ", body='" + body + '\'' +
                ", formData=" + formData +
                '}';
    }

    public String getPath() {
        return requestLine.getPath();
    }

    // TODO: getParameter로 메소드 이름 변경
    public String getAttributeFromQueryString(String key) {
        return requestLine.getQueryString().getParameter(key);
    }

    public String getAttributeFromForm(String key) {
        return formData.get(key);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }

    public String getBody() {
        return body;
    }

}
