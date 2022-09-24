package webserver.servlet;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpBody;
import webserver.http.HttpHeader;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestLine;
import webserver.http.response.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SignIn 기능 테스트")
class SignInServletTest {

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();

        User user = new User("javajigi", "password", "JaeSung", "javajigi@slipp.net");
        DataBase.addUser(user);
    }

    @Test
    @DisplayName("존재하는 사용자의 로그인 요청이 들어올 경우 처리한다.")
    void signIn() {
        HttpRequest httpRequest = createHttpRequest();

        SignInServlet sut = new SignInServlet();
        HttpResponse actual = sut.doPost(httpRequest);

        assertThat(actual.getResponseLine().toString()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(actual.getHttpHeaders().hasLocation()).isTrue();
        assertThat(actual.getHttpHeaders().getHeaders().get(HttpHeader.SET_COOKIE)).isEqualTo("logined=false; Path=/");
    }

    @Test
    @DisplayName("존재하지 않는 사용자의 로그인 요청이 들어올 경우 처리한다.")
    void signInFailed() {
        HttpRequest httpRequest = createHttpRequest();

        SignInServlet sut = new SignInServlet();
        HttpResponse actual = sut.doPost(httpRequest);

        assertThat(actual.getResponseLine().toString()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(actual.getHttpHeaders().hasLocation()).isTrue();
        assertThat(actual.getHttpHeaders().getHeaders().get(HttpHeader.SET_COOKIE)).isEqualTo("logined=false; Path=/");
    }

    private HttpRequest createHttpRequest() {
        RequestLine requestLine = RequestLine.parse("POST /user/login HTTP/1.1");
        HttpHeaders httpHeaders = HttpHeaders.init();
        httpHeaders.addRequestHeader("Host: localhost:8080");
        httpHeaders.addRequestHeader("Connection: keep-alive");
        httpHeaders.addRequestHeader("Content-Length: 29");
        httpHeaders.addRequestHeader("Content-Type: application/x-www-form-urlencoded");
        httpHeaders.addRequestHeader("Accept: */*");
        HttpBody httpBody = HttpBody.from("userId=javajigi&password=1234");

        return new HttpRequest(requestLine, httpHeaders, httpBody);
    }
}
