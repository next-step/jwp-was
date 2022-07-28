package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewControllerTest {
    @Test
    @DisplayName("HTTP Request가 들어왔을 때 원하는 파일을 잘 가져온다.")
    void handleHttpRequestTest() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(
                List.of(("GET /index.html HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").split("\n"))
        );

        byte[] result = new ViewController().handle(httpRequest);

        assertThat(result).containsExactly(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
    }
}
