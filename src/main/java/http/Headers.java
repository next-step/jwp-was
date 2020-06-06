package http;

import java.util.ArrayList;
import java.util.List;

public class Headers {
    private List<Header> headers = new ArrayList<>();

    public void add(String headerLine) {
        headers.add(new Header(headerLine));
    }

    public Header getHeader(HeaderName headerType) {
        return headers.stream()
                .filter(header -> header.exists(headerType))
                .findFirst()
                .orElse(null);
    }

    public List<Header> getHeaders() {
        return headers;
    }

}
