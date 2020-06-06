package webserver;

import http.request.Request;
import http.request.body.Body;
import http.request.headers.Headers2;
import http.request.requestline.requestLine2.RequestLine2;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.customhandler.StaticResourceHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class StaticResourceHandlerTest {
    @DisplayName("work메소드가 호출되면, url이 가리키는 static 자원 정보를 응답한다.")
    @Test
    void work() throws IOException, URISyntaxException {
        // given
        //given
        RequestLine2 requestLine = new RequestLine2("GET /css/style.css HTTP/1.1");
        Body body = new Body("");
        Headers2 headers = new Headers2(new HashMap<>());
        Request request = new Request(requestLine, headers, body);
        StaticResourceHandler handler = new StaticResourceHandler();

        //when
        Response response = handler.work(request);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getContentType()).isEqualTo(ContentType.CSS);
        assertThat(response.getBody())
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./statics/css/style.css"));
    }
}
