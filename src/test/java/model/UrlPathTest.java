package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlPathTest {

    @DisplayName("확장자 파싱 검증")
    @Test
    void hasExtensionTest() {
        UrlPath noFileUrlPath = new UrlPath("/domain/user");
        Assertions.assertThat(noFileUrlPath.hasExtension()).isFalse();

        UrlPath wrongFileUrlPath = new UrlPath("/index.htl");
        Assertions.assertThat(wrongFileUrlPath.hasExtension()).isFalse();

        UrlPath fileUrlPath1 = new UrlPath("/domain/index.html");
        Assertions.assertThat(fileUrlPath1.hasExtension()).isTrue();

        UrlPath fileUrlPath2 = new UrlPath("/index.html");
        Assertions.assertThat(fileUrlPath2.hasExtension()).isTrue();
    }
}