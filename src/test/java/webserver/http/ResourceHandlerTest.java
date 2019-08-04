package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.ResourceHandler;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceHandlerTest {

    @DisplayName("load resource by path")
    @ParameterizedTest(name = "path: {0}")
    @ValueSource(strings = {"/index.html", "/registration.html", "/test.js", "/test.css"})
    void loadResourceContent(String path) {
        ResourceHandler resourceHandler = new ResourceHandler(getClass().getClassLoader());
        assertThat(resourceHandler.getContents(path)).contains("hello test");
    }

}
