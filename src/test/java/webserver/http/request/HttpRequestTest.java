package webserver.http.request;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    void getSessionId() throws Exception {
        String reqHeader = "Host: localhost:8080\nCookie: SESSION-ID=12345";

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(reqHeader.getBytes())));
        HttpRequest request = HttpRequest.of(br);

        String sessionId = request.getSessionId();
        assertThat(sessionId).isEqualTo("12345");
    }
}


