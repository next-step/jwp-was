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
    @DisplayName("work메소드 호출 - css 파일")
    @Test
    void workForCss() throws IOException, URISyntaxException {
        //given
        RequestLine2 requestLine = new RequestLine2("GET /css/styles.css HTTP/1.1");
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
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./static/css/styles.css"));
    }

    @DisplayName("work메소드 호출 - Javascript 파일")
    @Test
    void workForJavascript() throws IOException, URISyntaxException {
        //given
        RequestLine2 requestLine = new RequestLine2("GET /js/scripts.js HTTP/1.1");
        Body body = new Body("");
        Headers2 headers = new Headers2(new HashMap<>());
        Request request = new Request(requestLine, headers, body);
        StaticResourceHandler handler = new StaticResourceHandler();

        //when
        Response response = handler.work(request);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getContentType()).isEqualTo(ContentType.JAVASCRIPT);
        assertThat(response.getBody())
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./static/js/scripts.js"));
    }
}
