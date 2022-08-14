package http.httprequest.requestbody;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class RequestBodyTest {

    @Test
    @DisplayName("빈 문자열로 제공하면 바디가 존재하지 않음")
    void emptyString() {
        assertThat(new RequestBody("")).isEqualTo(RequestBody.empty());
    }

    @Test
    @DisplayName("바디 정보 조회")
    void value() {
        //given
        RequestBody body = new RequestBody("userId=javajigi");
        //when, then
        assertThat(body.getValue("userId")).isEqualTo(Optional.of("javajigi"));
    }
}