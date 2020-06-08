package http.session;

import http.HttpSession;
import java.util.Optional;

public interface HttpSessionStorage {

    HttpSession newHttpSession(String id);

    Optional<HttpSession> getHttpSession(String id);
}
