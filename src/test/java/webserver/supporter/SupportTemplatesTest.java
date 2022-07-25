package webserver.supporter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SupportTemplatesTest {

    @ParameterizedTest
    @CsvSource(value = {"/qna/form.html", "/qna/show.html", "/user/form.html", "/user/profile.html", "/index.html"})
    void isSupportedTest(String supportedTemplate) {
        assertThat(SupportTemplates.isSupported(supportedTemplate)).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"/qna/form", "/show.html", "/user/.html", "/user/profile1.html", "/1/index.html"})
    void notSupportedTest(String notSupportedTemplate) {
        assertThat(SupportTemplates.isSupported(notSupportedTemplate)).isFalse();
    }

}
