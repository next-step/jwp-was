package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestBodyTest {
    @Test
    @DisplayName("Request Body가 String으로 주어졌을 때 key value로 파싱할 수 있다.")
    void parseTest() {
        String bodyString = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        RequestBody result = new RequestBody(bodyString);

        assertEquals(result.getValue("userId"), "javajigi");
        assertEquals(result.getValue("password"), "password");
        assertEquals(result.getValue("name"), "박재성");
        assertEquals(result.getValue("email"), "javajigi@slipp.net");
    }
}
