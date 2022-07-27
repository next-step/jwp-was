package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("요청 바디")
class RequestBodyTest {

    @Test
    @DisplayName("생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> RequestBody.from("userId=javajigi&password=password&name=JaeSung")),
                () -> assertThatNoException().isThrownBy(() -> RequestBody.from(""))
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("빈 문자열로 제공하면 바디가 존재하지 않음")
    void instance_emptyString_emtpyProperty(String body) {
        assertThat(RequestBody.from(body)).isEqualTo(RequestBody.empty());
    }

    @Test
    @DisplayName("바디 정보 조회")
    void value() {
        //given
        RequestBody body = RequestBody.from("userId=javajigi");
        //when, then
        assertThat(body.value("userId")).isEqualTo(Optional.of("javajigi"));
    }
}
