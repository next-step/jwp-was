package webserver.http.response.handler;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.handler.exception.InvalidRequestException;

class PostResponseGroupTest {


    @DisplayName("지원되지 않는 요청의 경우 예외가 발생해야 한다.")
    @Test
    void createFailed() {
        PostResponseGroup postResponseGroup = new PostResponseGroup();

        assertThatThrownBy(() -> postResponseGroup.getResponse("invalidIndex"))
                .isInstanceOf(InvalidRequestException.class);
    }
}
