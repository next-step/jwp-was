package utils;

import model.RequestLine;
import model.UrlPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import types.HttpMethod;
import types.Protocol;

class HandlerAdapterTest {

    @DisplayName("요청에 대해 적절한 handler를 찾아 invoke 하는지 검증")
    @Test
    void invoke() {
        RequestLine requestLine = new RequestLine(HttpMethod.GET, new UrlPath("/user"), Protocol.HTTP_1_1);
        String expected = (String) HandlerAdapter.getInstance().invoke(requestLine);

        Assertions.assertThat("getUserReturnValue").isEqualTo(expected);
    }
}
