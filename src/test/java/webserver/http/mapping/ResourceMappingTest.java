package webserver.http.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceMappingTest {
    @DisplayName("resource 확인")
    @ParameterizedTest
    @CsvSource({"/js/bootstrap.min.js,true", "/css/style.css,true", "/index.html,false"})
    void support(String requestUri, boolean result) {
        assertThat(ResourceMapping.support(requestUri)).isEqualTo(result);
    }

    @DisplayName("getContentType")
    @ParameterizedTest
    @CsvSource({"/fonts/glyphicons-halflings-regular.woff, text/fonts"
            , "/css/bootstrap.min.css, text/css"
            , "/js/scripts.js, text/js"})
    void getContentType(String requestUri, String expectedContentType) {
        //when
        String contentType = ResourceMapping.getContentType(requestUri);

        //then
        assertThat(contentType).isEqualTo(expectedContentType);
    }
}