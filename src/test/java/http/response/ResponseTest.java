package http.response;

import org.junit.jupiter.api.Test;

import static http.response.ResponseStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class ResponseTest {

    @Test
    void createResponse() {
        byte[] bytes = "Test".getBytes();
        Response response = Response.ofOk(bytes);

        assertThat(response.getStatus()).isEqualTo(OK);
        assertThat(response.getHeader()).hasSize(2);
        assertThat(response.getHeader()).contains(
                entry("Content-Type", "text/html;charset=utf-8"),
                entry("Content-Length", String.valueOf(bytes.length))
        );
        assertThat(response.getBody()).isEqualTo(bytes);
    }
}
