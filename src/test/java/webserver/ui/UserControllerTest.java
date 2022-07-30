package webserver.ui;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utils.FileIoUtils;
import webserver.application.UserService;
import webserver.domain.DefaultView;
import webserver.domain.HttpHeaders;
import webserver.domain.HttpRequest;
import webserver.domain.ResponseEntity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static webserver.ui.ConstantForUserControllerTest.GET_USER_CREATE_REQUEST;
import static webserver.ui.ConstantForUserControllerTest.POST_USER_CREATE_REQUEST;
import static webserver.ui.ConstantForUserControllerTest.POST_USER_LOGIN_REQUEST;
import static webserver.ui.ConstantForUserControllerTest.POST_USER_LOGIN_REQUEST_WITH_INVALID_PASSWORD;

class UserControllerTest {

    private static UserController userController;

    @BeforeAll
    static void setUp() {
        userController = new UserController(new UserService());
    }

    @DisplayName("유효한 요청 정보를 전달하면 다시 반환받아 확인할 수 있다.")
    @Test
    void usersTestApiWithValidPath() throws IOException {
        BufferedReader br = createBufferedReader(GET_USER_CREATE_REQUEST);
        HttpRequest httpRequest = HttpRequest.newInstance(br);
        HttpHeaders expectedHeaders = httpRequest.getHeaders();

        ResponseEntity<HttpRequest> response = userController.usersTestApi(httpRequest);
        HttpRequest result = response.getBody();
        HttpHeaders actualHeaders = result.getHeaders();


        assertAll(
                () -> assertThat(actualHeaders.getAttribute("Connection"))
                        .isEqualTo(expectedHeaders.getAttribute("Connection")),

                () -> assertThat(actualHeaders.getAttribute("Accept"))
                        .isEqualTo(expectedHeaders.getAttribute("Accept")),

                ()-> assertThat(actualHeaders.getContentLength())
                        .isEqualTo(expectedHeaders.getContentLength())
        );
    }

    @DisplayName("getUsers 메서드는 회원 가입 페이지 템플릿을 반환한다.")
    @Test
    void userFormWithAnyRequest() throws IOException, URISyntaxException {
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath("./templates/user/form.html");

        ResponseEntity<DefaultView> response = userController.userForm(null);
        DefaultView view = response.getBody();

        assertThat(view).hasToString(new String(expectedBody));
    }

    @DisplayName("loginFailed 메서드는 로그인 실패 페이지 템플릿을 반환한다.")
    @Test
    void loginFailedWithAnyRequest() throws IOException, URISyntaxException {
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath("./templates/user/login_failed.html");

        ResponseEntity<DefaultView> response = userController.loginFailed(null);
        DefaultView view = response.getBody();

        assertThat(view).hasToString(new String(expectedBody));
    }

    @DisplayName("createUser 메서드는 요청정보가 유효한 경우 index.html 템플릿으로 리다이렉트 시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {GET_USER_CREATE_REQUEST, POST_USER_CREATE_REQUEST})
    void createUserWithValidRequest(String validRequestLine) throws IOException, URISyntaxException {
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        BufferedReader br = createBufferedReader(validRequestLine);
        HttpRequest httpRequest = HttpRequest.newInstance(br);

        ResponseEntity<DefaultView> response = userController.createUser(httpRequest);
        HttpHeaders actualHeaders = response.getHeaders();
        DefaultView actualView = response.getBody();

        assertAll(
                ()-> assertThat(actualHeaders.getAttribute("Location")).isEqualTo("/index.html"),
                ()-> assertThat(actualView).hasToString(new String(expectedBody))
        );
    }

    @DisplayName("loginHtml 메서드는 요청정보와 무관하게 로그인 페이지 템플릿을 반환한다.")
    @Test
    void loginHtml() throws IOException, URISyntaxException {
        byte[] expectedBody = FileIoUtils.loadFileFromClasspath("./templates/user/login.html");

        ResponseEntity<DefaultView> response = userController.loginHtml(null);
        DefaultView actualView = response.getBody();

        assertThat(actualView).hasToString(new String(expectedBody));
    }

    @DisplayName("login 메서드는 요청 정보가 유효한 경우 logined=true 값을 쿠키에 추가해 index.html 페이지 템플릿을 반환한다.")
    @Test
    void loginWithValidLoginData() throws IOException, URISyntaxException {
        BufferedReader givenBr = createBufferedReader(POST_USER_CREATE_REQUEST);
        HttpRequest givenHttpRequest = HttpRequest.newInstance(givenBr);

        userController.createUser(givenHttpRequest);

        BufferedReader br = createBufferedReader(POST_USER_LOGIN_REQUEST);
        HttpRequest httpRequest = HttpRequest.newInstance(br);
        ResponseEntity<?> response = userController.login(httpRequest);
        HttpHeaders actualHeader = response.getHeaders();

        assertAll(
                ()-> assertThat(actualHeader.getAttribute("Location")).isEqualTo("/index.html")
        );
    }

    @DisplayName("login 메서드는 요청 정보가 유효하지 않은 경우 logined=true 값을 쿠키에 추가해 login_failed.html 페이지 템플릿을 반환한다.")
    @Test
    void loginWithInValidLoginData() throws IOException, URISyntaxException {
        BufferedReader givenBr = createBufferedReader(POST_USER_CREATE_REQUEST);
        HttpRequest givenHttpRequest = HttpRequest.newInstance(givenBr);

        userController.createUser(givenHttpRequest);

        BufferedReader br = createBufferedReader(POST_USER_LOGIN_REQUEST_WITH_INVALID_PASSWORD);
        HttpRequest httpRequest = HttpRequest.newInstance(br);
        ResponseEntity<?> response = userController.login(httpRequest);
        HttpHeaders actualHeader = response.getHeaders();

        assertAll(
                ()-> assertThat(actualHeader.getAttribute("Location")).isEqualTo("/user/login_failed.html")
        );
    }

    private static BufferedReader createBufferedReader(String requestLine) {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(requestLine.getBytes())));
    }

}
