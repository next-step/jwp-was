/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package session;

import java.util.UUID;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public class UuidRandomGenerator implements SessionIdGenerator {
    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
