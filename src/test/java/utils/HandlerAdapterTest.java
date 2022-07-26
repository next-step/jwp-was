package utils;

import model.HttpMessage;
import model.HttpMessageData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class HandlerAdapterTest {

    @DisplayName("요청에 대해 적절한 handler를 찾아 invoke 하는지 검증")
    @Test
    void invoke() {
        List<String> httpMessageData = List.of("GET /user HTTP/1.1");
        HttpMessage httpMessage = new HttpMessage(new HttpMessageData(httpMessageData));
        String expected = (String) HandlerAdapter.getInstance().invoke(httpMessage);

        Assertions.assertThat("getUserReturnValue").isEqualTo(expected);
    }
}
