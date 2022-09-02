package model;

import model.request.RequestBody;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestBodyTest {

    @Test
    void 바디_값가져오기() throws UnsupportedEncodingException {

        final String data = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        final RequestBody body = new RequestBody(data);

        assertThat(body.getFirstValue("userId")).isEqualTo("javajigi");
    }
}
