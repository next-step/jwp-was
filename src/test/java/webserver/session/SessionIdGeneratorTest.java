/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.session;

import org.junit.jupiter.api.Test;
import session.SessionIdGenerator;
import session.UuidRandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public class SessionIdGeneratorTest {

    @Test
    void session_uuid_generate_test() {
        SessionIdGenerator sessionIdGenerator = new UuidRandomGenerator();
        String uuid = sessionIdGenerator.generateId();

        System.out.println(uuid);
        assertThat(uuid).isNotEmpty();
    }
}
