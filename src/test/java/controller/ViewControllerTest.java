package controller;

import model.HttpHeader;
import model.HttpRequest;
import model.HttpResponse;
import org.junit.jupiter.api.Test;
import webserver.RequestLine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewControllerTest {

    @Test
    void static_파일_요청시_200응답() throws IOException, URISyntaxException {

        final ViewController controller = new ViewController();
        final HttpRequest httpRequest = createHttpRequest();
        final HttpResponse response = controller.process(httpRequest);

        assertThat(response.getMessages().get(0)).isEqualTo("HTTP/1.1 200 OK \r\n");
    }

    private HttpRequest createHttpRequest() throws UnsupportedEncodingException {
        final String data = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";

        return new HttpRequest(new HttpHeader(headers()), "");
    }

    private List<String> headers() {
        return Arrays.asList("GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Content-Length: 59",
                "Content-Type: application/x-www-form-urlencoded",
                "Accept: */*");
    }

}
