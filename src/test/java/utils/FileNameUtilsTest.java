package utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FileNameUtilsTest {

    @DisplayName("파일 경로를 입력하면 파일의 확장자를 반환 해야한다.")
    @CsvSource(value = {
            "index.html -> html",
            "/index.html -> html",
            "style.css -> css",
            "style.min.css -> css",
            "css/style.min.css -> css",
    }, delimiterString = " -> ")
    @ParameterizedTest
    void getExtensionTest(String path, String ext) {
        Assertions.assertThat(FileNameUtils.getExtension(path)).isEqualTo(ext);
    }


}