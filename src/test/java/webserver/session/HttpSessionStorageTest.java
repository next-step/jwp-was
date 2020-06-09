package webserver.session;


import static org.assertj.core.api.Assertions.assertThat;

import http.HttpSession;
import http.session.HttpSessionStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.LocalHttpSessionStorage;

class HttpSessionStorageTest {

    private HttpSessionStorage httpSessionStorage;

    @BeforeEach
    void init() {
        httpSessionStorage = new LocalHttpSessionStorage();
    }

    @Test
    void creatAndFindSession() {
        HttpSession httpSession = httpSessionStorage.newHttpSession();
        HttpSession findHttpSession = httpSessionStorage.getHttpSession(httpSession.getId())
            .orElse(null);
        assertThat(httpSession).isEqualTo(findHttpSession);
    }
}
