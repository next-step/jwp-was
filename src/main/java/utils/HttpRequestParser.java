package utils;

import http.requests.HttpRequestHeader;
import http.requests.parameters.FormData;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 요청을 파싱하는 클래스
 */
public class HttpRequestParser {

    private static final String REQUEST_HEADER_DELIMITER = ":";
    private static final String FORM_DATA_PAIR_DELIMITER = "&";
    private static final String FORM_DATA_KEY_VALUE_DELIMITER = "=";

    public static HttpRequestHeader parseHeaders(List<String> rawRequestHeaders) {
        final Map<String, String> parsedHeaderMap = rawRequestHeaders.stream()
                .map(data -> splitKeyAndValue(data, REQUEST_HEADER_DELIMITER))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue)
                );
        return new HttpRequestHeader(parsedHeaderMap);
    }

    public static FormData parseFormData(String formData) {
        final String decodedPathString = URLDecoder.decode(formData, StandardCharsets.UTF_8);
        final Map<String, String> parsedFormDataMap =
                Arrays.stream(decodedPathString.split(FORM_DATA_PAIR_DELIMITER))
                        .map(data -> splitKeyAndValue(data, FORM_DATA_KEY_VALUE_DELIMITER))
                        .collect(Collectors.toMap(
                                AbstractMap.SimpleImmutableEntry::getKey,
                                AbstractMap.SimpleImmutableEntry::getValue)
                        );
        return new FormData(parsedFormDataMap);
    }

    private static AbstractMap.SimpleImmutableEntry<String, String> splitKeyAndValue(String data, String delimiter) {
        final String[] split = data.split(delimiter, 2);    // delimiter를 포함하는 값이 올 수도 있으므로 명확히 키와 값만 나눔
        return new AbstractMap.SimpleImmutableEntry<>(split[0].trim(), split[1].trim());
    }

}
