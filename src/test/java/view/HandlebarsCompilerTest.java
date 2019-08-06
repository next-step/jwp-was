package view;

import db.DataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class HandlebarsCompilerTest {

    private ViewCompiler handlebarsCompiler = HandlebarsCompiler.of("/templates", ".html");

    @DisplayName("컴파일러 생성에 성공한다.")
    @Test
    void create() {
        // then
        assertThat(handlebarsCompiler).isNotNull();
    }

    @DisplayName("컴파일에 성공한다.")
    @Test
    void compile() throws IOException {
        // when
        final String userListView = handlebarsCompiler.compile("user/list", DataBase.findAll());

        // then
        assertThat(userListView).isNotNull();
    }

    @DisplayName("컴파일 시 파일을 못찾을 경우 예외처리한다.")
    @Test
    void compileFileNotFoundException() {
        assertThatExceptionOfType(FileNotFoundException.class)
                .isThrownBy(() -> handlebarsCompiler.compile("asdasd", DataBase.findAll()));
    }
}
