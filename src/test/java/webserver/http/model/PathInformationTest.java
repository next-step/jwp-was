package webserver.http.model;

import exception.IllegalHttpRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PathInformationTest {

    @DisplayName("path는 /를 시작으로하여 resource를 표기한다.")
    @Test
    void construct_notStartWithSeparator() {
        assertThatThrownBy(() -> new PathInformation("users")).isInstanceOf(IllegalHttpRequestException.class)
                                                              .hasMessageContaining("path는 /를 시작으로 하여 경로를 나타냅니다.");
    }

    @DisplayName("path에 query string을 추가하여 전송합니다.")
    @Test
    void construct() {
        PathInformation pathInformation = new PathInformation("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(pathInformation.getPath()).isEqualTo(new Path("/users"));
        assertThat(pathInformation.getQueryStrings()).isEqualTo(new QueryStrings("userId=javajigi&password=password&name=JaeSung"));
    }
}
