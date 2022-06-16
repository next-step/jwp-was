package webserver.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UriTest {

    @Test
    void from_emptyQuery() {
        // when
        Uri uri = Uri.from("/users");

        // then
        assertAll(
                () -> assertEquals(uri.getPath(), Path.from("/users")),
                () -> assertEquals(uri.getQuery(), Query.empty())
        );
    }

    @Test
    void from() {
        // when
        Uri uri = Uri.from("/users?userId=javajigi&password=password&name=JaeSung");

        // then
        assertAll(
                () -> assertEquals(uri.getPath(), Path.from("/users")),
                () -> assertEquals(uri.getQuery(), Query.from("userId=javajigi&password=password&name=JaeSung"))
        );
    }
}