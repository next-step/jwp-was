package webserver.controller;

import mvc.controller.UserCreateController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.HttpHeader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpStatusCode;
import webserver.WasTestTemplate;

import java.io.DataOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("회원가입 컨트롤러 테스트")
class UserCreateControllerTest {

    private WasTestTemplate testTemplate;
    private UserCreateController controller;
    private HttpResponse response;

    @BeforeEach
    void initEach() {
        testTemplate = new WasTestTemplate();
        controller = new UserCreateController();
        response = new HttpResponse();
    }

    @DisplayName("회원가입시 index.html로 리다이랙트")
    @Test
    void redirect() throws IOException {
        HttpRequest request = testTemplate.request("Http_POST.txt");
        DataOutputStream dos = new DataOutputStream(testTemplate.createOutputStream("Http_POST.txt"));

        controller.doPost(request, response);

        assertAll(
                () -> assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatusCode.CREATED),
                () -> assertThat(response.getResponseHeader(HttpHeader.LOCATION)).contains("/index.html")
        );
    }
}
