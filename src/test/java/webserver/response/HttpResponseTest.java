package webserver.response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.request.protocol.Protocol;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import http.response.StatusLine;
import mvc.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.HttpHeader;
import webserver.WasTestTemplate;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("HTTP 응답 테스트")
class HttpResponseTest {

    private WasTestTemplate testTemplate;
    private HttpResponse response;

    @BeforeEach
    void setUp() {
        testTemplate = new WasTestTemplate();
        response = new HttpResponse();
    }

    @DisplayName("Http Response 생성")
    @Test
    void createHttpResponse() throws FileNotFoundException {
        DataOutputStream dos = new DataOutputStream(testTemplate.createOutputStream("Http_Forward.txt"));
        StatusLine statusLine = StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK);

        assertAll(
                () -> assertThatNoException().isThrownBy(
                        () -> response.buildResponse(statusLine, HttpHeader.empty())),
                () -> assertThatNoException().isThrownBy(
                        () -> response.buildResponse(statusLine, HttpHeader.empty(), new byte[0])),
                () -> assertThatNoException().isThrownBy(
                        () -> response.buildResponse(statusLine, HttpHeader.empty(), "body"))
        );
    }

    @DisplayName("Http Response 생성 실패")
    @Test
    void createHttpResponse_FAIL() throws FileNotFoundException {
        DataOutputStream dos = new DataOutputStream(testTemplate.createOutputStream("Http_Forward.txt"));
        StatusLine statusLine = StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK);

        assertAll(
                () -> assertThatThrownBy(() -> response.buildResponse(null, HttpHeader.empty()))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> response.buildResponse(statusLine, null, new byte[0]))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @DisplayName("Content-Length 테스트")
    @Test
    void checkContentLength() throws FileNotFoundException {
        DataOutputStream dos = new DataOutputStream(testTemplate.createOutputStream("Http_Forward.txt"));
        StatusLine statusLine = StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK);
        String body = "body";

        response.buildResponse(statusLine, HttpHeader.empty(), body);

        assertThat(response.getContentLength())
                .isEqualTo(body.getBytes(StandardCharsets.UTF_8).length);
    }

    @DisplayName("Http 포워딩")
    @Test
    public void responseForward() throws IOException {
        DataOutputStream dos = new DataOutputStream(testTemplate.createOutputStream("Http_Body.txt"));
        StatusLine statusLine = StatusLine.of(Protocol.from("HTTP/1.1"), HttpStatusCode.OK);

        response.buildResponse(statusLine, HttpHeader.empty(), loadHtml().getBytes(StandardCharsets.UTF_8));
    }

    @DisplayName("Http 리다이렉션")
    @Test
    public void responseRedirect() throws FileNotFoundException {
        DataOutputStream dos = new DataOutputStream(testTemplate.createOutputStream("Http_Redirect.txt"));
        response.sendRedirect("/index.html");
    }

    @DisplayName("쿠키값 추가")
    @Test
    public void responseCookies() throws FileNotFoundException {
        DataOutputStream dos = new DataOutputStream(testTemplate.createOutputStream("Http_Cookie.txt"));

        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private String loadHtml() throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = handlebars.compile("profile");

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");
        return template.apply("/index.html");
    }
}
