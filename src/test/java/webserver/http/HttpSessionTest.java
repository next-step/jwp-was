package webserver.http;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @DisplayName("세션의 id를 조회한다.")
    @Test
    void getId() {
        String id = UUID.randomUUID().toString();
        HttpSession session = new HttpSession(id);
        assertThat(session.getId()).isEqualTo(id);
    }
}
