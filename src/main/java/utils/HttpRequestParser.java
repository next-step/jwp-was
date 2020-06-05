package utils;

import http.requests.HttpRequestHeader;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 요청을 파싱하는 클래스
 */
public class HttpRequestParser {

    public static final String REQUEST_HEADER_DELIMITER = ":";

    public static HttpRequestHeader parseHeaders(List<String> rawRequestHeaders) {
        final Map<String, String> parsedHeaderMap = rawRequestHeaders.stream()
                .map(data -> splitKeyAndValue(data, REQUEST_HEADER_DELIMITER))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue)
                );
        return new HttpRequestHeader(parsedHeaderMap);
    }

    private static AbstractMap.SimpleImmutableEntry<String, String> splitKeyAndValue(String data, String delimiter) {
        final String[] split = data.split(delimiter, 2);    // delimiter를 포함하는 값이 올 수도 있으므로 명확히 키와 값만 나눔
        return new AbstractMap.SimpleImmutableEntry<>(split[0].trim(), split[1].trim());
    }
}
