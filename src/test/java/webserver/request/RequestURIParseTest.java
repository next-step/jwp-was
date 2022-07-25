package webserver.request;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestURIParseTest {
    @Test
    @DisplayName("URI를 파싱하여 path와 params 정보를 얻을 수 있다.")
    void parseTest() {
        String uri = "/users?name=TEST&password=password";

        RequestURI requestURI = new RequestURI(uri);

        assertEquals(requestURI.getPath(), "/users");
        assertEquals(requestURI.getQueryString().getParams(), Map.of("name", "TEST", "password", "password"));
    }

    @Test
    @DisplayName("query string이 존재하지 않을 때 path만 파싱한다.")
    void parsePathTest() {
        String uri = "/users";

        RequestURI requestURI = new RequestURI(uri);

        assertEquals(requestURI.getPath(), "/users");
        assertNull(requestURI.getQueryString());
    }
}
