package http;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class HeadersTest {

    @Test
    void parserBody() {
        Headers headers = Headers
            .from(Arrays.asList("Content-Type: application/x-www-form-urlencoded"));

        String body = "userId=javajigi&password=password";

        Parameters parameters = headers.parseBody(body);
        assertThat(parameters.get("userId")).isEqualTo("javajigi");
        assertThat(parameters.get("password")).isEqualTo("password");
    }
}
