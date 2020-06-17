package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ContentTypeTest {

    @DisplayName("응답 리소스에 대응하는 미디어 타입 문자열 구하기")
    @Test
    void test_() {
        // given
        Uri uri = Uri.from("/user/index.html");
        // when
        ContentType contentType = ContentType.from(uri);
        String result = contentType.toStringWithCharsetUTF8();
        // then
        assertThat(contentType).isEqualTo(ContentType.HTML);
        assertThat(result).isEqualTo("text/html;charset=utf-8");
    }

    @Test
    void test_toRelativePath() {
        // given
        Uri uri = Uri.from("/user/index.html");
        // when
        String result = ContentType.toRelativePath(uri);
        // then
        assertThat(result).isEqualTo("./templates/user/index.html");
    }
}