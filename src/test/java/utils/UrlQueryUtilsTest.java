package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.platform.commons.util.ReflectionUtils;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("url 쿼리 유틸")
class UrlQueryUtilsTest {

    @Test
    @DisplayName("생성 불가")
    void instance_thrownAssertionError() {
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> ReflectionUtils.newInstance(UrlQueryUtilsTest.class));
    }

    @Test
    @DisplayName("맵으로 변경")
    void queryToMap() {
        assertThat(UrlQueryUtils.toMap("userId=javajigi&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net"))
                .isEqualTo(Map.of("userId", "javajigi", "name", "박재성", "email", "javajigi@slipp.net"));
    }

    @Test
    @DisplayName("키만 존재하면 빈 스트링으로 추가")
    void queryToMap_emptyValue_emtpyString() {
        assertThat(UrlQueryUtils.toMap("userId=&name="))
                .isEqualTo(Map.of("userId", "", "name", ""));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("값이 비어있으면 빈 맵")
    void queryToMap_empty_emptyMap(String string) {
        assertThat(UrlQueryUtils.toMap(string))
                .isEqualTo(Collections.emptyMap());
    }
}
