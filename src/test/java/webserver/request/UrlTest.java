package webserver.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlTest {

    @Test
    void from_emptyQuery() {
        // when
        Url url = Url.from("/users");

        // then
        assertAll(
                () -> assertEquals(url.getPath(), Path.from("/users")),
                () -> assertEquals(url.getQuery(), Query.empty())
        );
    }

    @Test
    void from() {
        // when
        Url url = Url.from("/users?userId=javajigi&password=password&name=JaeSung");

        // then
        assertAll(
                () -> assertEquals(url.getPath(), Path.from("/users")),
                () -> assertEquals(url.getQuery(), Query.from("userId=javajigi&password=password&name=JaeSung"))
        );
    }
}