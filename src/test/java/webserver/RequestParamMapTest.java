package webserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RequestParamMapTest {

    @Test
    void 주어진_경로로부터_Query_String_파싱_테스트() {
        String requestUrl = "/users?userId=kkwan0226&password=password&name=kkwan";
        RequestParamMap requestParamMap = RequestParamMap.from(requestUrl);

        assertAll(
                () -> assertThat(requestParamMap.get("userId")).isEqualTo("kkwan0226"),
                () -> assertThat(requestParamMap.get("password")).isEqualTo("password"),
                () -> assertThat(requestParamMap.get("name")).isEqualTo("kkwan")
        );
    }
}
