package http.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class HttpEntityTest {

    @ParameterizedTest
    @NullAndEmptySource
    void getBodyForNullAndEmpty(String body) {
        HttpEntity httpEntity = new HttpEntity(new HttpHeaders(Collections.emptyMap()), body);

        assertThat(httpEntity.getBody()).isNullOrEmpty();
        assertThat(httpEntity.getBodyMap()).isEmpty();
    }

    @Test
    void getBody() {
        String body = "userId=ninjasul&password=1234&name=%EB%B0%95%EB%8F%99%EC%97%BD&email=ninjasul%40gmail.com";

        HttpEntity httpEntity = new HttpEntity(new HttpHeaders(Collections.emptyMap()), body);

        assertThat(httpEntity.getBody()).isNotEmpty();
        assertThat(httpEntity.getBodyMap()).isNotEmpty();

        assertThat(httpEntity.getBodyMap().get("userId")).isEqualTo("ninjasul");
        assertThat(httpEntity.getBodyMap().get("password")).isEqualTo("1234");
        assertThat(httpEntity.getBodyMap().get("name")).isEqualTo("박동엽");
        assertThat(httpEntity.getBodyMap().get("email")).isEqualTo("ninjasul@gmail.com");
    }
}