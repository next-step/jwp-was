package http.httpresponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static http.httpresponse.HttpStatusCode.OK;
import static org.assertj.core.api.Assertions.assertThat;


class HttpStatusCodeTest {

    @Test
    @DisplayName("toString 테스트")
    void toStringTest() {
        assertThat(OK.toString()).hasToString("200 OK");
    }

}