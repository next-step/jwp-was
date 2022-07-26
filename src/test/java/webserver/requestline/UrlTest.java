package webserver.requestline;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlTest {

    @DisplayName("URL을 파싱한다.")
    @Test
    void parseUrl(){
        String input = "/users?userId=javajigi&password=password&name=JaeSung";
        Map<String, String> expectedQueryParameters = Map.of(
                "userId", "javajigi",
                "password", "password",
                "name", "JaeSung"
        );

        Url url = Url.from(input);

        assertAll(
                () -> assertEquals("/users", url.getPath()),
                () -> assertEquals(expectedQueryParameters, url.getQueryParameter().getParameters())
        );
    }
}
