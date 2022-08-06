package http.request.session;

public interface SessionStore {
    HttpSession fetch(String sessionId);
}
