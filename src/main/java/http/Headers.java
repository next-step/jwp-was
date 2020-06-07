package http;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Headers {
    private List<Header> headers = new ArrayList<>();

    public void add(String headerLine) {
        headers.add(new Header(headerLine));
    }

    public Optional<Header> getHeader(HeaderName headerType) {
        return this.headers.stream()
                .filter(header -> header.exists(headerType))
                .findFirst();
    }

    public List<Header> getHeaders() {
        return headers;
    }

}
