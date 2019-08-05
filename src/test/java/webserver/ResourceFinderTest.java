package webserver;

import model.http.UriPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceFinderTest {
    @ParameterizedTest
    @ValueSource(strings = {"/css/styles.css", "/qna/form.html"})
    void find(UriPath resourcePath) {
        assertThat(ResourceFinder.find(resourcePath).isPresent()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/stylessss.css", "/qna/formmmm.html"})
    void fail_find_for_no_exist_file(UriPath resourcePath) {
        assertThat(ResourceFinder.find(resourcePath).isPresent()).isFalse();
    }
}
