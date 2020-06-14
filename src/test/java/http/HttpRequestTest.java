package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {

    @Test
    void http_request_test() {
        String line ="GET /index.html HTTP/1.1";

        HttpRequest httpRequest = new HttpRequest(RequestLineParser.parse(line));

        assertThat(httpRequest.getPath()).isEqualTo("/index.html");
    }

    void http_request_header_test() {
        String line ="POST /user/login HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 34\n" +
                "Cache-Control: max-age=0";

        while (!"".equals(line)) {
            line = br.readLine();
            logger.debug("header : {}", line);
            if (line.contains("Content-Length")) {
                String value = line.split(" ")[1];
                contentLength = Integer.parseInt(value);
            }
            if (line.contains("Cookie")) {
                loginStatus = loginCheck(line);
            }
        }

    }
}
