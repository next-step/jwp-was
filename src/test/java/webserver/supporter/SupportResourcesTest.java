package webserver.supporter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SupportResourcesTest {

    @DisplayName("지원하는 정적파일 확장자")
    @ParameterizedTest
    @CsvSource(value = {"/static/css/styles.css", "/static/js/scripts.js"})
    void staticResourceSuccessTest(String path) {
        assertThat(SupportResources.isSupportedExtension(path)).isTrue();
    }

    @DisplayName("지원하지 않는 정적파일 확장자")
    @ParameterizedTest
    @CsvSource(value = {"/static/css/styles.zxcc", "/ab/cd/scripts.jsss"})
    void staticResourceFailTest(String path) {
        assertThat(SupportResources.isSupportedExtension(path)).isFalse();
    }

}
