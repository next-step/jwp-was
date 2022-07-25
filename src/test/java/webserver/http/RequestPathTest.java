package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestPathTest {

    @Test
    void RequestPasth_파싱_테스트() {
        String requestPathWithQueryString = "/users?userId=javajigi&password=password&name=JaeSung";
        String[] splitRequestPath = requestPathWithQueryString.split("\\?");
        RequestPath requestPath = new RequestPath(requestPathWithQueryString);

        assertAll(
                () -> assertThat(requestPath.getPath()).isEqualTo("/users"),
                () -> assertThat(requestPath.getQueryString()).isEqualTo(splitRequestPath[1])
        );
    }
}