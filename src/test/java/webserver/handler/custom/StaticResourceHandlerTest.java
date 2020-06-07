package webserver.handler.custom;

import http.request.Request;
import http.request.RequestBody;
import http.request.Headers;
import http.request.RequestLine;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class StaticResourceHandlerTest {
    @DisplayName("work메소드 호출 - css 파일")
    @Test
    void workForCss() throws IOException, URISyntaxException {
        //given
        RequestLine requestLine = new RequestLine("GET /css/styles.css HTTP/1.1");
        RequestBody requestBody = new RequestBody("");
        Headers headers = new Headers(new HashMap<>());
        Request request = new Request(requestLine, headers, requestBody);
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
        RequestLine requestLine = new RequestLine("GET /js/scripts.js HTTP/1.1");
        RequestBody requestBody = new RequestBody("");
        Headers headers = new Headers(new HashMap<>());
        Request request = new Request(requestLine, headers, requestBody);
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
