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

public class TemplateResourcesHandlerTest {
    @DisplayName("Template의 work메소드가 호출되면, url이 가리키는 template페이지 콘텐츠를 byte[]로 읽여온다.")
    @Test
    void work() throws IOException, URISyntaxException {
        //given
        RequestLine requestLine = new RequestLine("GET /index.html HTTP/1.1");
        RequestBody requestBody = new RequestBody("");
        Headers headers = new Headers(new HashMap<>());
        Request request = new Request(requestLine, headers, requestBody);
        TemplateResourceHandler templateResourceHandler = new TemplateResourceHandler();

        //when
        Response response = templateResourceHandler.work(request);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getContentType()).isEqualTo(ContentType.HTML);
        assertThat(response.getBody())
                .isEqualTo(FileIoUtils.loadFileFromClasspath("./templates/index.html"));
    }
}
