package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static http.response.ResponseStatus.FOUND;
import static http.response.ResponseStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class HttpResponseTest {

    @DisplayName("200 응답을 생성한다.")
    @Test
    void createOkResponse() {
        HttpResponse response = new HttpResponse(createDataOutputStream());
        response.returnFile("/index.html");

        assertThat(response.getStatus()).isEqualTo(OK);
        assertThat(response.getHeader()).hasSize(2);
        assertThat(response.getHeader()).contains(
                entry("Content-Type", "text/html;charset=utf-8"));
    }

    @DisplayName("302 응답을 생성한다.")
    @Test
    void createFoundResponse() {
        HttpResponse response = new HttpResponse(createDataOutputStream());
        response.redirect("/index.html");

        assertThat(response.getStatus()).isEqualTo(FOUND);
        assertThat(response.getHeader()).hasSize(1);
        assertThat(response.getHeader()).contains(
                entry("Location", "/index.html")
        );
        assertThat(response.getBody()).hasSize(0);
    }

    private static DataOutputStream createDataOutputStream() {
        return new DataOutputStream(
                new OutputStream() {
                    @Override
                    public void write(int b) throws IOException {
                    }
                });
    }

}
