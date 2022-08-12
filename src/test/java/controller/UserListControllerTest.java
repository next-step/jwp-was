package controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("UserListController 테스트")
class UserListControllerTest {

    private static UserListController userListController;

    @BeforeAll
    static void setUp() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        userListController = new UserListController(new Handlebars(loader));
    }

    @DisplayName("쿠키에 로그인 정보가 있으면 유저 리스트 조회 성공")
    @Test
    void success() throws Exception {
        HttpRequest request = HttpRequest.of(
                RequestLine.of(HttpMethod.GET, Path.of("/user/list"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(List.of("Host: www.nowhere123.com", "Accept-Language: en-us", "Set-Cookie: logined=true; Path=/")),
                HttpRequestBody.empty()
        );

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse response = HttpResponse.of(dataOutputStream);

        HttpResponse result = userListController.execute(request, response);

        assertAll(
                () -> assertThat(result.getHttpResponseCode()).isEqualTo("200 OK"),
                () -> assertThat(result.getHeaders()).contains(
                        Map.entry(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8"))
        );
    }

    @DisplayName("쿠키에 로그인 정보가 없으면 유저 리스트 조회 실패")
    @Test
    void failed() throws Exception {
        HttpRequest request = HttpRequest.of(
                RequestLine.of(HttpMethod.GET, Path.of("/user/list"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(List.of("Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*", "Accept-Language: en-us")),
                HttpRequestBody.empty()
        );

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse response = HttpResponse.of(dataOutputStream);

        HttpResponse result = userListController.execute(request, response);

        assertAll(
                () -> assertThat(result.getHttpResponseCode()).isEqualTo("302 FOUND"),
                () -> assertThat(result.getHeaders()).contains(
                        Map.entry(HttpHeaders.LOCATION, "/user/login.html"))
        );
    }

}
