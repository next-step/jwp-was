package webserver.servlet;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpBody;
import webserver.http.HttpHeaders;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestLine;
import webserver.http.response.HttpResponse;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("SignUp 기능 테스트")
class SignUpServletTest {

    @BeforeEach
    void setUp() {
        DataBase.deleteAll();
    }

    @Test
    @DisplayName("회원가입 요청이 들어올 경우 처리한다.")
    void signUp() {
        RequestLine requestLine = RequestLine.parse("POST /user/create HTTP/1.1");
        HttpHeaders httpHeaders = HttpHeaders.create();
        httpHeaders.addHeader("Host: localhost:8080");
        httpHeaders.addHeader("Connection: keep-alive");
        httpHeaders.addHeader("Content-Length: 59");
        httpHeaders.addHeader("Content-Type: application/x-www-form-urlencoded");
        httpHeaders.addHeader("Accept: */*");
        HttpBody httpBody = HttpBody.from("userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net");

        HttpRequest httpRequest = new HttpRequest(requestLine, httpHeaders, httpBody);

        SignUpServlet sut = new SignUpServlet();
        HttpResponse actual = sut.doPost(httpRequest);

        Optional<User> foundUser = DataBase.findUserById("javajigi");

        assertThat(actual.getResponseLine().toString()).isEqualTo("HTTP/1.1 302 Found");
        assertThat(actual.getHttpHeaders().hasLocation()).isTrue();
        assertThat(actual.getHttpBody().getContents()).isEmpty();
        assertThat(foundUser.isPresent()).isTrue();
    }
}
