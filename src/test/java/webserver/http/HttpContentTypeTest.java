package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HttpContentTypeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "html:./templates:text/html;charset=utf-8",
            "ico:./templates:text/html;charset=utf-8",
            "css:./static:text/css,*/*;q=0.1",
            "js:./static:application/javascript"
    }, delimiter = ':')
    void 확장자에_맞은_컨텐츠타입조회(String type, String path, String value) {
        HttpContentType contentType = HttpContentType.of(type);

        assertThat(contentType.getType()).isEqualTo(type);
        assertThat(contentType.getResourcePath()).isEqualTo(path);
        assertThat(contentType.getValue()).isEqualTo(value);
    }
}