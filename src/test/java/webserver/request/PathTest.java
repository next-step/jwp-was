package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PathTest {

    @DisplayName("빈 문자열은 파싱할 수 없다")
    @ParameterizedTest(name = "#{index}: [{arguments}]")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void empty_strings_cannot_be_parsed(final String emptyPath) {
        assertThatThrownBy(() -> Path.parse(emptyPath))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("빈 문자열은 파싱할 수 없습니다.");
    }

    @DisplayName("location, query string이 존재하는 Path를 생성한다")
    @Test
    void create_path_with_location_and_query_string() {
        final Path actual = Path.parse("/users?userId=1234");

        assertThat(actual).isEqualTo(Path.of("/users", "userId=1234"));
    }

    @DisplayName("location만 존재하는 Path를 생성한다")
    @Test
    void create_path_with_location() {
        final Path actual = Path.parse("/users");

        assertThat(actual).isEqualTo(Path.from("/users"));
    }

}
