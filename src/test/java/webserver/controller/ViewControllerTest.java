package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewControllerTest {
    @Test
    @DisplayName("HTTP Request가 들어왔을 때 원하는 파일을 잘 가져온다.")
    void handleHttpRequestTest() throws Exception {
        HttpRequest htmlRequest = new HttpRequest(
                List.of(("GET /index.html HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").split("\n"))
        );

        HttpRequest cssRequest = new HttpRequest(
                List.of(("GET /css/styles.css HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: text/css,*/*;q=0.1").split("\n"))
        );

        HttpResponse htmlResult = new ViewController().execute(htmlRequest);
        HttpResponse cssResult = new ViewController().execute(cssRequest);

        assertArrayEquals(htmlResult.getBody(), FileIoUtils.loadFileFromClasspath("./templates/index.html"));
        assertArrayEquals(cssResult.getBody(), FileIoUtils.loadFileFromClasspath("./static/css/styles.css"));
    }
}
