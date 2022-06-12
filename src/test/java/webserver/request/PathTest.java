package webserver.request;

import org.junit.jupiter.api.Test;
import webserver.request.Path;
import webserver.request.Query;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {

    @Test
    void from() {
        // when
        Path path = Path.from("/users?userId=javajigi&password=password&name=JaeSung");

        // then
        assertAll(
                () -> assertEquals(path.getEntry(), "/users"),
                () -> assertEquals(path.getQuery(), Query.from("userId=javajigi&password=password&name=JaeSung"))
        );
    }
}