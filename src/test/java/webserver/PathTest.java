package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    void empty_strings_cannot_be_parsed(String emptyPath) {
        assertThatThrownBy(() -> Path.from(emptyPath))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("QueryString이 없는 경우 파싱")
    @ParameterizedTest
    @ValueSource(strings = {
        "/users",
        "/users?"
    })
    void has_no_query_string(String uri) {
        final Path path = Path.from(uri);

        assertAll(
            () -> assertThat(path.getLocation()).isEqualTo("/users"),
            () -> assertThat(path.getQueryString()).isEmpty()
        );
    }

    @DisplayName("QueryString이 있는 경우 파싱")
    @Test
    void has_query_string() {
        final Path path = Path.from("/users?userId=1234");

        assertAll(
            () -> assertThat(path.getLocation()).isEqualTo("/users"),
            () -> assertThat(path.getQueryString()).isEqualTo("userId=1234")
        );
    }

}
