package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestBodyTest {

    @DisplayName("Body를 파싱한다.")
    @Test
    void parseBody(){
        String body = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Map<String, String> parsedBody = Map.of(
                "userId", "javajigi",
                "password", "password",
                "name", "박재성",
                "email", "javajigi@slipp.net"
        );

        RequestBody requestBody = RequestBody.parseFrom(body);

        assertEquals(parsedBody, requestBody.getContents());
    }

    @DisplayName("빈 Body는 빈 Map을 리턴한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void parseEmptyBody(String input){
        RequestBody requestBody = RequestBody.parseFrom(input);

        assertEquals(Collections.EMPTY_MAP, requestBody.getContents());
    }
}
