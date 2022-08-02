package webserver.http.request.header;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.exception.InvalidUriException;

class UriTest {

    @DisplayName("path는 / 로 시작되어야 한다.")
    @Test
    void createFailedBySlash() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Uri("user"))
                .isInstanceOf(InvalidUriException.class)
                .hasMessage("유효한 uri가 아닙니다.");
    }

    @DisplayName("생성")
    @Test
    void create() {
        // given
        String path = "/user";

        // when
        Uri actual = new Uri(path);

        // then
        assertThat(actual.toString()).isEqualTo(path);
    }
}
