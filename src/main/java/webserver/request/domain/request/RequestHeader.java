package webserver.request.domain.request;

import org.springframework.util.StringUtils;
import webserver.exception.StringEmptyException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestHeader {

    private static final String DELIMITER = ": ";
    private static final int MIN_HEADER_LENGTH = 2;

    private Map<String, String> headerMap = new HashMap<>();

    public RequestHeader() {};

    public RequestHeader(List<String> list) {
        list.stream()
                .map(li -> li.split(": "))
                .forEach(arr -> headerMap.put(arr[0], arr[1]));
    };

    private void validate(String[] header) {
        if(header.length != MIN_HEADER_LENGTH) {
            throw new StringEmptyException("header 속성을 확인해주세요.");
        }

        if (!StringUtils.hasText(header[0]) || !StringUtils.hasText(header[1])) {
            throw new StringEmptyException("header 속성을 확인해주세요.");
        }
    }

    public void addHeaderProperty(String header) {
        String[] headerMap = header.split(DELIMITER);

        validate(headerMap);

        this.headerMap.put(headerMap[0], headerMap[1]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHeader that = (RequestHeader) o;
        return Objects.equals(headerMap, that.headerMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerMap);
    }
}
