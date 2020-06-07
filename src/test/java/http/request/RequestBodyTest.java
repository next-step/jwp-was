package http.request;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBodyTest {
    @Test
    void of() {
        RequestBody requestBody = RequestBody.parse("userId=parkeeseul&password=password&name=박이슬&email=parkeeseul@slipp.net");
        Map bodyMap = requestBody.getBodyMap();

        assertThat(bodyMap.get("userId")).isEqualTo("parkeeseul");
        assertThat(bodyMap.get("password")).isEqualTo("password");
        assertThat(bodyMap.get("email")).isEqualTo("parkeeseul@slipp.net");
        assertThat(bodyMap.get("name")).isEqualTo("박이슬");
    }
}
