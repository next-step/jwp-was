package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestBodyTest {

    @DisplayName("content-type 이 application/x-www-form-urlencoded 인 request body 를 만들 수 있다.")
    @Test
    void parseTest() {
        // given
        RequestBody requestBody = new RequestBody("userId=javajigi&password=password&name=재성&email=javajigi@slipp.net");

        // when // then
        assertThat(requestBody.getValue("userId")).isEqualTo("javajigi");
        assertThat(requestBody.getValue("password")).isEqualTo("password");
        assertThat(requestBody.getValue("name")).isEqualTo("재성");
        assertThat(requestBody.getValue("email")).isEqualTo("javajigi@slipp.net");

    }

}
