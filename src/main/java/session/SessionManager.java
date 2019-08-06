/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package session;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public interface SessionManager {
    HttpSession createSession();
    HttpSession createSession(String sessionId);
    HttpSession getSession(String sessionId);
}
