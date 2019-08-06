/*
 * Copyright (c) 2019 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package webserver.session;

import exception.SessionInvalidException;
import org.junit.jupiter.api.Test;
import session.HttpSession;
import session.HttpSessionImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public class HttpSessionTest {
    private static final String TEST_SESSION_ID = "test_session_id";

    @Test
    void http_session_id_test() {
        HttpSession httpSession = new HttpSessionImpl(TEST_SESSION_ID);
        assertThat(httpSession.getId()).isNotEmpty();
    }

    @Test
    void http_session_put_get_attribute_test() {
        //given
        String key = "key";
        String value = "test";
        HttpSession httpSession = new HttpSessionImpl(TEST_SESSION_ID);

        //when
        httpSession.setAttribute(key, value);

        //then
        assertThat(httpSession.getAttribute(key)).isEqualTo(value);
    }

    @Test
    void http_session_duplication_put_attribute_test() {
        //given
        String key = "key";
        String value1 = "test1";
        String value2 = "test2";
        HttpSession httpSession = new HttpSessionImpl(TEST_SESSION_ID);

        //when
        httpSession.setAttribute(key, value1);
        httpSession.setAttribute(key, value2);

        //then
        assertThat(httpSession.getAttribute(key)).isEqualTo(value2);
    }

    @Test
    void http_session_remove_attribute_test() {
        //given
        String key = "key";
        String value = "test";
        HttpSession httpSession = new HttpSessionImpl(TEST_SESSION_ID);

        //when
        httpSession.setAttribute(key, value);
        assertThat(httpSession.getAttribute(key)).isEqualTo(value);
        httpSession.removeAttribute(key);

        //then
        assertThat(httpSession.getAttribute(key)).isNull();
    }

    @Test
    void http_session_invalidate_test() {
        //given
        String key = "key";
        String value = "test";
        HttpSession httpSession = new HttpSessionImpl(TEST_SESSION_ID);

        //when
        httpSession.setAttribute(key, value);
        assertThat(httpSession.getAttribute(key)).isEqualTo(value);
        httpSession.invalidate();

        //then
        assertThrows(SessionInvalidException.class, () -> httpSession.getAttribute(key));
    }
}
