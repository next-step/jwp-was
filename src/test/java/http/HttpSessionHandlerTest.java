package http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpSessionHandlerTest {

    @Test
    void getSessionTest() {
        HttpSession session = new HttpSession();
        HttpSessionHandler.addSession(session);
        assertEquals(session,HttpSessionHandler.getSession(session.getId()));
    }
}
