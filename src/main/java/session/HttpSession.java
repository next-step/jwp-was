/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package session;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public interface HttpSession {
    String getId();
    void setAttribute(String name, Object value);
    Object getAttribute(String name);
    void removeAttribute(String name);
    void invalidate();
}
