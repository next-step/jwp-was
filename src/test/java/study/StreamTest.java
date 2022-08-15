package study;

import model.Cookie;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamTest {

    @Test
    void 쿠키스트링으로_맵을_만든다() {

        String cookieString = "a=1; b=2; c=2";

        final Map<String, Cookie> collect = Arrays.stream(cookieString.split("; "))
                .map(item -> new Cookie(item.split("=")[0], item.split("=")[1]))
                .collect(Collectors.toMap(Cookie::getName, self -> self));

        assertThat(collect.get("a").getName()).isEqualTo("a");
        assertThat(collect.get("a").getValue()).isEqualTo("1");
        assertThat(collect.get("c").getValue()).isEqualTo("2");
    }

    @Test
    void header_리스트로_맵만든다() {

        List<String> headers = Arrays.asList("Host: localhost:8080\n",
                "Connection: keep-alive\n",
                "Content-Length: 59\n");

        final Map<String, String> collect = headers.stream()
                .map(header -> header.split(": "))
                .collect(Collectors.toMap(item -> item[0], item -> item[1].strip()));

        assertThat(collect.get("Host")).isEqualTo("localhost:8080");
    }
}
