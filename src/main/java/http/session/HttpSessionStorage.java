package http.session;

import http.HttpSession;
import java.util.Optional;

public interface HttpSessionStorage {

    String SESSION_ID_NAME ="SESSION-ID";

    HttpSession newHttpSession();
    Optional<HttpSession> getHttpSession(String id);
}
