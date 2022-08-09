package webserver.http.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBodyTest {

    @Test
    void construct() {
        String requestBodyText = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        RequestBody requestBody = new RequestBody(requestBodyText);
        assertThat(requestBody.getRequestBodyMap()).hasSize(4);
    }
}
