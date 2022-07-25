package webserver;

import db.DataBase;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.step.LoginAcceptanceStep;
import webserver.step.StaticAcceptanceStep;
import webserver.step.TemplateAcceptanceStep;
import webserver.step.UserAcceptanceStep;

import java.util.Collections;

@DisplayName("http 요청 인수 테스트")
class HttpRequestTest {

    private static final String RANDOM_PORT = RandomStringUtils.randomNumeric(4);
    private static final Thread WAS = new Thread(() -> {
        try {
            WebApplicationServer.main(new String[]{RANDOM_PORT});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });

    @BeforeAll
    static void beforeAll() {
        WAS.start();
        RestAssured.port = Integer.parseInt(RANDOM_PORT);
    }

    @AfterAll
    static void afterAll() {
        WAS.interrupt();
    }

    @Test
    @DisplayName("/index.html 로 접속했을 때 index.html 파일을 읽어 클라이언트에 응답")
    void requestIndexHtml() {
        //when
        ExtractableResponse<Response> indexPageResponse = TemplateAcceptanceStep.requestTemplatePage("/index.html");
        //then
        TemplateAcceptanceStep.succeedRendering(indexPageResponse);
    }

    @Test
    @DisplayName("/form.html 으로 접근하여 회원가입")
    void requestFormAndRegisterUser() {
        //when
        ExtractableResponse<Response> formPageResponse = TemplateAcceptanceStep.requestTemplatePage("/user/form.html");
        //then
        TemplateAcceptanceStep.succeedRendering(formPageResponse);
        //when
        ExtractableResponse<Response> registeredResponse = UserAcceptanceStep.requestUserRegister("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        //then
        UserAcceptanceStep.registeredUser(registeredResponse, "javajigi");
    }

    @Test
    @DisplayName("/login.html 페이지에서 로그인 성공")
    void loginSucceed() {
        //given
        UserAcceptanceStep.requestUserRegister("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        //when
        ExtractableResponse<Response> loginPageResponse = TemplateAcceptanceStep.requestTemplatePage("/user/login.html");
        //then
        TemplateAcceptanceStep.succeedRendering(loginPageResponse);
        //when
        ExtractableResponse<Response> loginResponse = LoginAcceptanceStep.requestLogin("userId=javajigi&password=password");
        //then
        LoginAcceptanceStep.succeedLogin(loginResponse);
    }

    @Test
    @DisplayName("로그인 실패")
    void loginFailed() {
        //when
        ExtractableResponse<Response> loginResponse = LoginAcceptanceStep.requestLogin("userId=anonymous&password=password");
        //then
        LoginAcceptanceStep.redirectFailedLogin(loginResponse);
    }

    @Test
    @DisplayName("로그인하고 사용자 목록 출력")
    void userList_loggedIn() {
        //given
        UserAcceptanceStep.requestUserRegister("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        ExtractableResponse<Response> loginResponse = LoginAcceptanceStep.requestLogin("userId=javajigi&password=password");
        //when
        ExtractableResponse<Response> response = UserAcceptanceStep.requestUserList(loginResponse.cookies());
        //then
        UserAcceptanceStep.retrievedUserList(response);
    }

    @Test
    @DisplayName("로그인되지 않은 상태로 사용자 리스트 조회하면 로그인 페이지 이동")
    void userList_failedLoggedIn() {
        //when
        ExtractableResponse<Response> response = UserAcceptanceStep.requestUserList(Collections.emptyMap());
        //then
        UserAcceptanceStep.redirectedLoginPage(response);
    }

    @Test
    @DisplayName("스타일 시트 파일 조회")
    void requestStyleSheet() {
        //when
        ExtractableResponse<Response> response = StaticAcceptanceStep.requestStaticFile("./css/styles.css");
        //then
        StaticAcceptanceStep.succeedRetrieveFile(response, "text/css");
    }

    @AfterEach
    void tearDown() {
        DataBase.deleteAll();
    }
}
