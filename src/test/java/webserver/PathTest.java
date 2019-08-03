package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PathTest {

    @ParameterizedTest
    @ValueSource(strings = {"/test/test", "/test/test*/test"})
    void of(String path) {
        Path.of(path);
    }
}
