package model.http;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class UriPathTest {

    @ParameterizedTest
    @ValueSource(strings = {"/test/test", "/test/test*/test"})
    void of(String path) {
        UriPath.of(path);
    }

    @Test
    void prepend() {
        UriPath path2 = UriPath.of("/test3/test4");
        UriPath resultPath = UriPath.of("/test1/test2/test3/test4");

        assertThat(path2.prepend("/test1/test2")).isEqualTo(resultPath);
    }
}
