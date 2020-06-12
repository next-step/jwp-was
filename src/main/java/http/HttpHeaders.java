package http;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Collectors;
import utils.Args;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HttpHeaders {

    protected static final String ILLEGAL_HEADER = "유효하지 않은 Header 입니다.";
    private static final String HEADER_DELIMITER = ": ";
    private static final String CRLF = "\r\n";

    private final Map<String, String> headers = Maps.newHashMap();

    public void addHeader(String line) {
        String[] values = line.split(HEADER_DELIMITER, -1);
        Args.check(values.length == 2, ILLEGAL_HEADER);
        headers.put(values[0].trim(), values[1].trim());
    }

    public void addHeader(String key, String value) {
        headers.put(Args.notNull(key, ILLEGAL_HEADER), value);
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    @Override
    public String toString() {
        return headers.keySet().stream()
            .map(key -> key + HEADER_DELIMITER + headers.get(key) + CRLF)
            .collect(Collectors.joining(""));
    }
}
