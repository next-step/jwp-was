/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package session;

import exception.SessionInvalidException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public class HttpSessionImpl implements HttpSession {
    private final String id;
    private final ConcurrentMap<String, Object> attributes;

    private volatile boolean invalid;

    public HttpSessionImpl(final String sessionId) {
        this.id = sessionId;
        this.attributes = new ConcurrentHashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setAttribute(String name, Object value) {
        if (invalid) {
            throw new SessionInvalidException();
        }
        attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        if (invalid) {
            throw new SessionInvalidException();
        }
        return attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        if (invalid) {
            throw new SessionInvalidException();
        }
        attributes.remove(name);
    }

    @Override
    public void invalidate() {
        invalid = true;
    }
}
