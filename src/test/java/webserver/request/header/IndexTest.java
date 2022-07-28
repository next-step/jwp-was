package webserver.request.header;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.request.header.exception.InvalidUriException;

class IndexTest {

    @DisplayName("path는 / 로 시작되어야 한다.")
    @Test
    void createFailedBySlash() {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Index("user"))
                .isInstanceOf(InvalidUriException.class)
                .hasMessage("유효한 index가 아닙니다.");
    }

    @DisplayName("생성")
    @Test
    void create() {
        // given
        String path = "/user";

        // when
        Index actual = new Index(path);

        // then
        assertThat(actual.toString()).isEqualTo(path);
    }
}