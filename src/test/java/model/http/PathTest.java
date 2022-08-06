package model.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {
    private static final String TEMPLATE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    @DisplayName("html 페이지는 static Resource 입니다.")
    @Test
    void isStaticResource_html() {
        Path path = new Path("/index.html");
        assertThat(path.isStaticResource()).isTrue();
    }

    @DisplayName("확장자명이 없다면 Resource가 아닙니다.")
    @Test
    void isStaticResource_action() {
        Path path = new Path("/user/create");
        assertThat(path.isStaticResource()).isFalse();
    }

    @DisplayName("입력받은 하위 파일 경로를 적절하게 상위 파일 경로를 추가하여 전체 파일 경로로 변경한다.")
    @ParameterizedTest
    @CsvSource(value = {"/index.html:./templates", "/favicon.ico:./templates", "/js/scripts.js:./static",
            "/css/styles.css:./static"}, delimiter = ':')
    void resource(String childPath, String parentPath) {
        Path path = new Path(childPath);
        assertThat(path.resource()).isEqualTo(parentPath + path.getPath());
    }

    @DisplayName("정적 파일이 아닌 경로를 요청하였을 때 처리")
    @Test
    void resource_notResource() {
        Path path = new Path("/user/create");
        assertThat(path.resource()).isEqualTo(path.getPath());
    }
}