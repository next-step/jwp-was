package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@DisplayName("QueryString 파싱 테스트")
class QueryStringTest {

    @Test
    void create() {
        // Arrange
        final String inputQueryString = "userId=javajigi&password=password&name=JaeSung";

        // Act
        final QueryString actual = new QueryString(inputQueryString);

        // Assert
        assertThat(actual.getParameterMap()).hasSize(3);
        assertThat(actual.getParameterMap()).contains(
                entry("userId", "javajigi"), entry("password", "password"), entry("name", "JaeSung")
        );
    }
}