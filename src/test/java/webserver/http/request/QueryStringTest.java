package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("쿼리 스트링")
class QueryStringTest {

    @Test
    @DisplayName("생성")
    void instance() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> QueryString.from("userId=javajigi&password=password&name=JaeSung")),
                () -> assertThatNoException().isThrownBy(() -> QueryString.from(""))
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("빈 문자열로 제공하면 쿼리 정보가 존재하지 않음")
    void instance_emptyString_emtpyProperty(String query) {
        assertThat(QueryString.from(query)).isEqualTo(QueryString.empty());
    }

    @Test
    @DisplayName("속성 값 조회")
    void value() {
        //given
        QueryString query = QueryString.from("userId=javajigi");
        //when, then
        assertThat(query.value("userId")).isEqualTo(Optional.of("javajigi"));
    }
}
