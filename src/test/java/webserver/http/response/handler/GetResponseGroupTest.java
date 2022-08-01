package webserver.http.response.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetResponseGroupTest {

    @DisplayName("지원되지 않는 요청에 대해선 기본 핸들러가 호출 되어야 한다.")
    @Test
    void createDefault() {
        GetResponseGroup getResponseGroup = new GetResponseGroup();

        assertThat(getResponseGroup.getResponse("invalidRequest"))
                .isInstanceOf(DefaultResponseHandler.class);
    }
}
