package webserver.domain;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;

public enum Sessions {
    INSTANCE;

    private Map<String, HttpSession> sessions = Maps.newHashMap();

    public HttpSession get(String sessionId) {
        if (Strings.isNullOrEmpty(sessionId)) {
            HttpSession httpSession = new HttpSession();
            sessions.put(httpSession.getId(), httpSession);
            return httpSession;
        }
        return Optional.ofNullable(this.sessions.get(sessionId))
            .orElse(HttpSession.empty());
    }

}
