package webserver.request;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.Header;

import java.io.BufferedReader;
import java.io.IOException;
import static org.assertj.core.api.Assertions.*;

class RequestBodyTest {

    RequestBody requestBody;

    @BeforeEach
    void setup() throws IOException {
        BufferedReader br = HelpData.postHelpData();
        int contentLength = Header.parsing(br).getContentLength();
        requestBody = RequestBody.parsing(br, contentLength);
    }

    @Test
    @DisplayName("요청에 대한 body가 잘 파싱되는지 확인")
    void parsing_request_body() throws IOException {
        BufferedReader br = HelpData.postHelpData();
        int contentLength = Header.parsing(br).getContentLength();
        RequestBody parsingRequestBody = RequestBody.parsing(br, contentLength);
        assertThat(parsingRequestBody.getBody().get("userId")).isEqualTo("javajigi");
        assertThat(parsingRequestBody.getBody().get("password")).isEqualTo("password");
    }

    @Test
    @DisplayName("요청에 대한 body가 user로 변환이 잘 되는지 확인")
    void convert_body_to_user() {

        User convertUser = requestBody.bodyToUser();
        User expectedUser = new User("javajigi", "password", "%EB%B0%95%EC%9E%AC%EC%84%B1", "javajigi%40slipp.net");
        assertThat(convertUser).isEqualTo(expectedUser);
    }

}