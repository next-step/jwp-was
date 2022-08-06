package webserver.http.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ExtensionTest {
    @DisplayName("파일 확장자에 따라 해당 enum 추출")
    @ParameterizedTest
    @CsvSource(value = {"html:HTML", "ico:ICO", "js:JS", "css:CSS"}, delimiter = ':')
    void getEnum(String extension, Extension parentPath) {
        assertThat(Extension.getEnum(extension)).isEqualTo(parentPath);
    }

    @DisplayName("파일 확장자에 따라 파일 상위 경로 추출")
    @ParameterizedTest
    @CsvSource(value = {"html:./templates", "ico:./templates", "js:./static", "css:./static"}, delimiter = ':')
    void parentPath(String extension, String parentPath) {
        assertThat(Extension.parentPathByExtension(extension)).isEqualTo(parentPath);
    }

}