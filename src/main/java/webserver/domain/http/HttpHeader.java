package webserver.domain.http;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpHeader {

    private final static String HEADER_DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> header;
    private final Cookies cookies;

    private HttpHeader(Map<String, String> header, Cookies cookies) {
        this.header = header;
        this.cookies = cookies;
    }

    public static HttpHeader from(List<String> request) {
        Map<String, String> collect = request.stream()
                .filter(line -> !line.isEmpty())
                .map(line -> line.split(HEADER_DELIMITER))
                .collect(Collectors.toUnmodifiableMap(
                        keyValue -> keyValue[0],
                        keyValue -> keyValue[1]
                ));

        Cookies cookies = Optional.ofNullable(collect.get(Cookie.COOKIE_KEY_NAME))
                .map(Cookies::from)
                .orElse(new Cookies());

        return new HttpHeader(collect, cookies);
    }

    public String get(String key) {
        return Optional.ofNullable(header.get(key))
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getContentLength() {
        try {
            return Integer.parseInt(header.get(CONTENT_LENGTH));
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isLogined() {
        Cookie cookie = cookies.get("logined");
        return Objects.nonNull(cookie) && Boolean.parseBoolean(cookie.getValue());
    }

    public Cookies getCookies() {
        return cookies;
    }
}
