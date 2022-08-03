package controller;

import model.HttpRequest;
import model.HttpResponse;
import org.junit.jupiter.api.Test;
import webserver.RequestLine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewControllerTest {

    @Test
    void static_파일_요청시_200응답() throws IOException, URISyntaxException {

        final ViewController controller = new ViewController();
        final HttpRequest httpRequest = createHttpRequest();
        final HttpResponse response = controller.process(httpRequest);

        for (String s : response.getMessages()) {
            System.out.println(s);
        }
        assertThat(response.getMessages().get(0)).isEqualTo("HTTP/1.1 200 OK \r\n");
    }

    private HttpRequest createHttpRequest() throws UnsupportedEncodingException {
        final String data = "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";

        return new HttpRequest(new RequestLine(data), "");
    }
}
