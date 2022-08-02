package webserver.domain;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.servlet.http.HttpSession;

public enum Sessions {
    INSTANCE;

    private Map<String, HttpSession> sessions = Maps.newHashMap();

    public HttpSession get(String sessionId) {
        if (Strings.isNullOrEmpty(sessionId)) {
            return new HttpCustomSession();
        }
        return this.sessions.get(sessionId);
    }

    public HttpCustomSession create() {
        HttpCustomSession session = newSession();
        this.sessions.put(session.getId(), session);
        return session;
    }

    private HttpCustomSession newSession() {
        return new HttpCustomSession();
    }

}
