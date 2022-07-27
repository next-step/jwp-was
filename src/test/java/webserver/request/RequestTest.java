package webserver.request;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.HttpMethod;

import java.io.BufferedReader;

public class RequestTest {

    @Test
    public void request_POST() throws Exception {
        BufferedReader br = HelpData.postHelpData();
        HttpRequest request = HttpRequest.parsing(br);
        Assertions.assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        Assertions.assertThat(request.getRequestUri()).isEqualTo("/users");
        Assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        Assertions.assertThat(request.getParameter("userId")).isEqualTo("javajigi");
    }

    @Test
    public void request_GET() throws Exception {
        BufferedReader br = HelpData.getHelpData("GET");
        HttpRequest request = HttpRequest.parsing(br);
        Assertions.assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        Assertions.assertThat(request.getRequestUri()).isEqualTo("/users?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        Assertions.assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }
}
