package utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticFileTest {

    @Test
    @DisplayName("요청 파일에 대하여 static인지 template인지 구분한다.")
    void request_file_check() {
        String templateUri = "/index.html";
        String staticUri = "/css/styles.css";

        Assertions.assertThat(StaticFile.isStatic(templateUri)).isFalse();
        Assertions.assertThat(StaticFile.isStatic(staticUri)).isTrue();
    }

}