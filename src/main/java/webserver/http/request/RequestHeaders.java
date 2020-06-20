package webserver.http.request;

import lombok.EqualsAndHashCode;
import utils.StringUtils;
import webserver.http.HttpHeader;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@EqualsAndHashCode
public class RequestHeaders {

    private Map<HttpHeader, RequestHeader> requestHeaders;

    public RequestHeaders(Map<HttpHeader, RequestHeader> requestHeaderTexts) {
        this.requestHeaders = requestHeaderTexts;
    }

    public static RequestHeaders of(List<String> requestHeaderTexts) {
        Map<HttpHeader, RequestHeader> requestHeaderMap = requestHeaderTexts.stream()
                .filter(StringUtils::isNotBlank)
                .map(RequestHeader::of)
                .collect(toMap(RequestHeader::getName, Function.identity(), (requestHeader1, requestHeader2) -> requestHeader1));
        return new RequestHeaders(requestHeaderMap);
    }

    public String get(HttpHeader name) {
        RequestHeader requestHeader = requestHeaders.get(name);
        if (Objects.isNull(requestHeader)) {
            return null;
        }
        return requestHeader.getValue();
    }
}
