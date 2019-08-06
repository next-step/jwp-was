/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.session;

import org.junit.jupiter.api.Test;
import session.HttpSession;
import session.InMemorySessionManager;
import session.SessionManager;
import session.UuidRandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public class SessionManagerTest {

    @Test
    void create_session_by_inmemory_session_manager() {
        SessionManager sessionManager = new InMemorySessionManager(new UuidRandomGenerator());
        HttpSession session = sessionManager.createSession();

        assertThat(session).isNotNull();
        assertThat(session.getId()).isNotEmpty();
    }

    @Test
    void duplicated_create_session_by_inmemory_session_manager() {
        SessionManager sessionManager = new InMemorySessionManager(new UuidRandomGenerator());
        HttpSession session = sessionManager.createSession();

        assertThat(session).isNotNull();
        assertThat(session.getId()).isNotEmpty();

        HttpSession dup_session = sessionManager.createSession(session.getId());
        assertThat(session).isEqualTo(dup_session);
    }

    @Test
    void get_session_by_inmemory_session_manager() {
        SessionManager sessionManager = new InMemorySessionManager(new UuidRandomGenerator());
        HttpSession session = sessionManager.createSession();

        assertThat(session).isNotNull();
        assertThat(session.getId()).isNotEmpty();
        assertThat(sessionManager.getSession(session.getId())).isEqualTo(session);
    }

    @Test
    void not_exist_get_session_by_inmemory_session_manager() {
        SessionManager sessionManager = new InMemorySessionManager(new UuidRandomGenerator());

        assertThat(sessionManager.getSession("not_exist_id")).isNull();
    }
}
