package webserver.http.request.body;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestBodyTest {
    @Test
    void create_and_getBodyValue() {
        HttpRequestBody httpRequestBody = new HttpRequestBody("userId=thxwelchs&password=password&name=%EC%9D%B4%ED%83%9C%ED%9B%88&email=thxwelchs%40gmail.com");

        assertThat(httpRequestBody.getBodyValue("userId")).isEqualTo("thxwelchs");
        assertThat(httpRequestBody.getBodyValue("password")).isEqualTo("password");
        assertThat(httpRequestBody.getBodyValue("name")).isEqualTo("이태훈");
        assertThat(httpRequestBody.getBodyValue("email")).isEqualTo("thxwelchs@gmail.com");
    }
}
