package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import webserver.RequestLine.PathAndQueryStrings;

class PathAndQueryStringsTest {

    @Test
    void createOnlySlashTest() {
        PathAndQueryStrings pathAndQueryStrings = PathAndQueryStrings.of("/");

        assertThat(pathAndQueryStrings.path).isEqualTo("/");
    }

    @Test
    void createEmptyPathFailTest() {
        assertThatThrownBy(
            () -> PathAndQueryStrings.of("")
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void createNullFailTest() {
        assertThatThrownBy(
            () -> PathAndQueryStrings.of(null)
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void createPathAndQueryStringsTest() {
        PathAndQueryStrings pathAndQueryStrings
            = PathAndQueryStrings.of("/user/create?userId=testId&password=testPw&name=testName&email=testEmail@email.com");

        assertThat(pathAndQueryStrings.path).isEqualTo("/user/create");
        assertThat(pathAndQueryStrings.queryStringsMap)
            .hasSize(4)
            .contains(entry("userId", "testId"))
            .contains(entry("password", "testPw"))
            .contains(entry("name", "testName"))
            .contains(entry("email", "testEmail@email.com"));
    }

}
