package request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestHeaderTest {

    @Test
    @DisplayName("값을 반환합니다.")
    void getParameterTest() {
        List<String> header = List.of("Cookie: logined=true");

        RequestHeader requestHeader = RequestHeader.from(header);

        assertThat(requestHeader.getHeader("Cookie")).isEqualTo("logined=true");
    }

    @Test
    @DisplayName("쿠키값을 반환합니다.")
    void getCookieTest() {
        List<String> header = List.of("Cookie: logined=true");

        RequestHeader requestHeader = RequestHeader.from(header);
        RequestCookie cookie = new RequestCookie(Map.of("logined", "true"));

        assertThat(requestHeader.getCookie()).isEqualTo(cookie);
    }

}
