package http;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    @Test
    public void parseTest() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new StringReader(
                "GET /users?key=1 HTTP/1.1\n" +
                        "Accept: text/html\n" +
                        "User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"
        ));


        HttpRequest httpRequest = new HttpRequest(bufferedReader);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getPath()).isEqualTo("/users");
        assertThat(httpRequest.getHeader("Accept")).isEqualTo("text/html");
        assertThat(httpRequest.getParameter("key")).isEqualTo("1");
    }
}
