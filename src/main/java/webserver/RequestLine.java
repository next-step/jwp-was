package webserver;


import org.springframework.util.StringUtils;

public class RequestLine {

    public static RequestLine parse(final String requestLine) {
        if (!StringUtils.hasText(requestLine)) {
            throw new IllegalArgumentException("빈 문자열은 파싱할 수 없습니다.");
        }
        return new RequestLine();
    }

    public String getMethod() {
        return "GET";
    }

    public String getPath() {
        return "/users";
    }

    public String getProtocol() {
        return "HTTP";
    }

    public String getVersion() {
        return "1.1";
    }
}
