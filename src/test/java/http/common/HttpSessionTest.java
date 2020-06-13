package http.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

    @Test
    @DisplayName("HttpSession을 정상적으로 생성할 수 있다")
    void createHttpSession() {
        new HttpSession();
    }

}