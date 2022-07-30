package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpBodyTest {

    @Test
    void create() {
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        HttpBody httpBody = new HttpBody(body);

        assertThat(httpBody).isNotNull();
    }
}
