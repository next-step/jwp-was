package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("url 쿼리스트링 파서")
class QueryStringParserTest {

    @Test
    @DisplayName("맵으로 변경")
    void queryToMap() {
        assertThat(QueryStringParser.parse("userId=javajigi&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"))
                .isEqualTo(Map.of("userId", "javajigi", "name", "박재성", "email", "javajigi@slipp.net"));
    }

    @Test
    @DisplayName("키만 존재하면 빈 스트링으로 추가")
    void queryToMapEmptyValueEmtpyString() {
        assertThat(QueryStringParser.parse("userId=&name="))
                .isEqualTo(Map.of("userId", "", "name", ""));
    }

    @Test
    @DisplayName("값이 비어있으면 빈 맵")
    void queryToEmptyMap() {
        assertThat(QueryStringParser.parse(""))
                .isEqualTo(Collections.emptyMap());
    }
}