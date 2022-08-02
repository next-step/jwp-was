package webserver.request.domain.request;

import java.io.BufferedReader;
import java.io.IOException;
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

    public Header(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static Header from(BufferedReader br) throws IOException {
        String line = br.readLine();

        Map<String, String> headerMap = new HashMap<>();
        while (!line.equals("")) {
            line = br.readLine();
            System.out.println(line);
            String[] values = line.split(DELIMITER);

            if (values.length == 2) {
                headerMap.put(values[0], values[1]);
            }
        }

        return new Header(headerMap);
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
