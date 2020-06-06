package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.response.ResponseStatus.FOUND;
import static http.response.ResponseStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class ResponseTest {

    @DisplayName("200 응답을 생성한다.")
    @Test
    void createOkResponse() {
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

    @DisplayName("302 응답을 생성한다.")
    @Test
    void createFoundResponse() {
        Response response = Response.ofFound("/index.html");

        assertThat(response.getStatus()).isEqualTo(FOUND);
        assertThat(response.getHeader()).hasSize(1);
        assertThat(response.getHeader()).contains(
                entry("Location", "/index.html")
        );
        assertThat(response.getBody()).hasSize(0);
    }

}
