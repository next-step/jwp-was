package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ContentTypeTest {
    @DisplayName("확장자에 맵핑된 Content-Type 반환")
    @ParameterizedTest
    @CsvSource(value = {".css:CSS", ".js:JAVASCRIPT", ".woff:WOFF",
            ".html:HTML", ".ico:ICO", ".ttf:TTF"}, delimiter = ':')
    void findContentType(String suffix, ContentType contentType) {
        //given
        String url = "index" + suffix;

        //when
        ContentType contentType1 = ContentType.findContentType(url);

        //then
        assertThat(contentType1).isEqualTo(contentType);
    }

    @DisplayName("등록되지 않은 suffix로 url이 끝나면 예외 반환")
    @Test
    void findContentTypeWhenNotRegistered() {
        //given
        String url = "index.ttttto";

        //when, then
        assertThatIllegalArgumentException().isThrownBy(() -> {
            ContentType.findContentType(url);
        }).withMessageContaining("Content-Type");
    }
}
