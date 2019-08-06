/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package session;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public class InMemorySessionManager implements SessionManager {
    private final SessionIdGenerator sessionIdGenerator;
    private final ConcurrentMap<String, HttpSession> sessions;

    public InMemorySessionManager(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
        this.sessions = new ConcurrentHashMap<>();
    }

    private HttpSession createSessionById(String sessionId) {
        HttpSession session = new HttpSessionImpl(sessionId);
        return Optional.ofNullable(sessions.putIfAbsent(sessionId, session))
                .orElse(session);
    }

    @Override
    public HttpSession createSession() {
        String sessionId = sessionIdGenerator.generateId();
        return createSessionById(sessionId);
    }

    @Override
    public HttpSession createSession(String sessionId) {
        return createSessionById(sessionId);
    }

    @Override
    public HttpSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

}
