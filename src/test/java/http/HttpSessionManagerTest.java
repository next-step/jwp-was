package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpSessionManagerTest {
    private HttpSessionManager sut;
    @Test
    void findSessionById_null() {
        // given
        String sessionId = null;

        // when
        HttpSession sessionBy = sut.findSessionById(sessionId);

        // then
        assertThat(sessionBy).isNull();
    }

}