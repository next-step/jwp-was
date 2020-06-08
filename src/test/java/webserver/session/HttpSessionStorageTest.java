package webserver.session;


import static org.assertj.core.api.Assertions.assertThat;

import http.HttpSession;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import http.session.HttpSessionStorage;
import http.session.LocalHttpSessionStorage;

class HttpSessionStorageTest {
    private HttpSessionStorage httpSessionStorage;

    @BeforeEach
    void init(){
        httpSessionStorage = new LocalHttpSessionStorage();;
    }
    @Test
    void creatAndFindSession(){
        String id = UUID.randomUUID().toString();

        HttpSession httpSession = httpSessionStorage.newHttpSession(id);
        HttpSession findHttpSession = httpSessionStorage.getHttpSession(id).orElse(null);
        assertThat(httpSession).isEqualTo(findHttpSession);
    }
}
