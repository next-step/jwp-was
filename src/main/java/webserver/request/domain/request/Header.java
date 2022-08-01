package webserver.request.domain.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Header {

    private static final String DELIMITER = ": ";

    private Map<String, String> headerMap = new HashMap<>();

    public Header() {};

    public Header(List<String> list) {
        list.stream()
                .map(li -> li.split(": "))
                .forEach(arr -> headerMap.put(arr[0], arr[1]));
    };

    public void addHeaderProperty(String header) {
        String[] headerMap = header.split(DELIMITER);

        if(headerMap.length == 2) {
            this.headerMap.put(headerMap[0], headerMap[1]);
        }
    }

    public String getHeader(String header) {
        return headerMap.get(header);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Header that = (Header) o;
        return Objects.equals(headerMap, that.headerMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerMap);
    }
}
