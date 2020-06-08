package session;

import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.StringUtil;

import java.util.Objects;
import java.util.UUID;

public class SessionManager {
    private static final String SUID = "suid"; //session uid
    private final InMemorySessionHolder inMemorySessionHolder;

    public SessionManager(final InMemorySessionHolder inMemorySessionHolder) {
        this.inMemorySessionHolder = inMemorySessionHolder;
    }

    public void loadSession(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String sessionId = loadSessionId(httpRequest, httpResponse);

        HttpSession httpSession = loadSession(sessionId);

        httpRequest.setSession(httpSession);
    }

    private String loadSessionId(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        String sessionId = httpRequest.getCookie(SUID);

        if (StringUtil.isEmpty(sessionId)) {
            sessionId = generateSUID(httpResponse);
        }

        return sessionId;
    }

    private String generateSUID(final HttpResponse httpResponse) {
        String sessionId = UUID.randomUUID().toString();
        httpResponse.setCookie(SUID, sessionId);

        return sessionId;
    }

    private HttpSession loadSession(final String sessionId) {
        HttpSession httpSession = inMemorySessionHolder.load(sessionId);

        if (Objects.isNull(httpSession)) {
            httpSession = DefaultHttpSession.of(sessionId);
        }

        return httpSession;
    }
}
