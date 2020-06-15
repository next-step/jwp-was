package webserver.request;

import lombok.EqualsAndHashCode;
import utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@EqualsAndHashCode
public class RequestHeaders {

    private Map<String, RequestHeader> requestHeaders;

    public RequestHeaders(Map<String, RequestHeader> requestHeaderTexts) {
        this.requestHeaders = requestHeaderTexts;
    }

    public static RequestHeaders of(List<String> requestHeaderTexts) {
        Map<String, RequestHeader> requestHeaderMap = requestHeaderTexts.stream()
                .filter(StringUtils::isNotBlank)
                .map(RequestHeader::of)
                .collect(toMap(RequestHeader::getName, Function.identity()));
        return new RequestHeaders(requestHeaderMap);
    }
}
