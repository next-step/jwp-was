package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("URL Parser 유틸 테스트")
class QueryUrlParserTest {

    @DisplayName("쿼리 파싱 성공 테스트")
    @Test
    void parseUrl() {
        assertThat(QueryUrlParser.toMap("userId=javajigi&password=password&name=JaeSung"))
                .isEqualTo(Map.of(
                        "userId", "javajigi",
                        "password", "password",
                        "name", "JaeSung"));
    }

    @DisplayName("키만 존재할 경우 빈 문자열로 추가")
    @Test
    void emptyValueToEmptyString() {
        assertThat(QueryUrlParser.toMap("userId=&name="))
                .isEqualTo(Map.of("userId", "", "name", ""));
    }

    @DisplayName("값이 빈경우 빈 맵 반환")
    @ParameterizedTest
    @NullAndEmptySource
    void emptyMap(String query) {
        assertThat(QueryUrlParser.toMap(query))
                .isEqualTo(Collections.emptyMap());
    }
}
