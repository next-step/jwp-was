package http;

import http.request.Uri;
import http.response.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

    @DisplayName("응답 리소스에 대응하는 미디어 타입 문자열 구하기")
    @Test
    void test_() {
        // given
        Uri uri = Uri.from("/user/index.html");
        // when
        ContentType contentType = ContentType.from(uri.getExtension());
        String result = contentType.toStringWithCharsetUTF8();
        // then
        assertThat(contentType).isEqualTo(ContentType.HTML);
        assertThat(result).isEqualTo("text/html;charset=utf-8");
    }
}