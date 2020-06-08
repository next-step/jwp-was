package http.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cookies {
    private List<Cookie> cookies = new ArrayList<>();

    public Cookies(List<Cookie> cookies) {
        this.cookies = new ArrayList<>(cookies);
    }

    public int getSize() {
        return cookies.size();
    }

    public void addCookie(Cookie cookie) {
        this.cookies.add(cookie);
    }

    @Override
    public String toString() {
        return cookies.stream()
                .map(c -> c.toString())
                .collect(Collectors.joining("&"));
    }
}
