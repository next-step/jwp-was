package handler;

import model.request.HttpRequestMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dummy.HttpRequestHeaderDummy.GET_INDEX_HTTP_REQUEST_DUMMY;
import static org.assertj.core.api.Assertions.assertThat;

class IndexHandlerTest {
    @Test
    @DisplayName("첫번째 리소스 경로가 공백인 HttpRequestHeader는 true를 반환한다.")
    void canHandlingTest() {
        HttpRequestMessage getIndexHttpRequest = GET_INDEX_HTTP_REQUEST_DUMMY;
        AbstractHandler indexHandler = new IndexHandler();

        Boolean result = indexHandler.canHandling(getIndexHttpRequest);

        assertThat(result).isTrue();
    }
}
