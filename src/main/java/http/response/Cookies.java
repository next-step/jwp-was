package http.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cookies {
    private List<Cookie> cookies = new ArrayList<>();

    public Cookies(List<Cookie> cookies) {
        this.cookies = Collections.unmodifiableList(cookies);
    }

    public int getSize() {
        return cookies.size();
    }

    @Override
    public String toString() {
        return cookies.stream()
                .map(c -> c.toString())
                .collect(Collectors.joining("&"));
    }
}
