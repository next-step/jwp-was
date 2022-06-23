package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.Protocol;
import webserver.http.request.Version;

import static org.assertj.core.api.Assertions.assertThatCode;

class ResponseLineTest {

    @DisplayName("ResponseLine을 생성할수 있다.")
    @Test
    void create() {
        assertThatCode(() -> new ResponseLine(Protocol.HTTP, Version.of("1.1")));
    }

}
