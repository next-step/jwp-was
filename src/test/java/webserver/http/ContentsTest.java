package webserver.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ContentsTest {

    @Test
    void create() {
        String bodyContents = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Contents actual = Contents.from(bodyContents);

        Map<String, String> expect = Map.of(
                "userId", "javajigi",
                "password", "password",
                "name", "박재성",
                "email", "javajigi@slipp.net"
        );

        assertThat(actual.getContents()).containsExactlyInAnyOrderEntriesOf(expect);
    }
}
