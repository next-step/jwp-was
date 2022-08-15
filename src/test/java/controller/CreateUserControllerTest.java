package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("CreateUserController 테스트")
class CreateUserControllerTest {

    private CreateUserController createUserController;
    private HttpRequest request;

    @BeforeEach
    void setUp() {
        createUserController = new CreateUserController();
        request = HttpRequest.of(
                RequestLine.of(HttpMethod.POST, Path.of("/user/create"), new String[]{"HTTP", "1.1"}),
                HttpRequestHeader.of(List.of("Host: www.nowhere123.com", "Accept: image/gif, image/jpeg, */*", "Accept-Language: en-us")),
                HttpRequestBody.of("userId=javajigi&password=password&name=JaeSung&email=koola976@gmail.com")
        );
    }

    @DisplayName("method,path 가 일치하면 true 리턴한다.")
    @Test
    void matchTrue() {
        boolean result = createUserController.match(request);
        assertThat(result).isTrue();
    }

    @DisplayName("302 코드, Location 헤더를 응답한다.")
    @Test
    void response() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse response = HttpResponse.of(dataOutputStream);

        HttpResponse result = createUserController.execute(request, response);
        assertAll(
                () -> assertThat(result.getHttpResponseCode()).isEqualTo("302 FOUND"),
                () -> assertThat(result.getHeaders()).contains(
                        Map.entry(HttpHeaders.LOCATION, "/index.html")
                )
        );

    }

}
