package webserver.http.request.requestline;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpPathComponentsBuilderTest {

    @Test
    void create_and_toPathComponents() {
        List<String> httpPathComponents = HttpPathComponentsBuilder.validateAndBuild("/users/hello/world");
        assertThat(httpPathComponents).isEqualTo(Arrays.asList("users", "hello", "world"));
    }
}
