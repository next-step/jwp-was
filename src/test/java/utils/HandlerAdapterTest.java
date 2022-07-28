package utils;

import model.ClientResponse;
import model.HttpMessage;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class HandlerAdapterTest {

    @DisplayName("요청에 대해 적절한 handler를 찾아 invoke 하는지 검증")
    @Test
    void invokeTest() throws IOException, InvocationTargetException, IllegalAccessException {
        List<String> httpMessageData = List.of("GET /user HTTP/1.1");
        HttpMessage httpMessage = new HttpMessage(httpMessageData);

        ClientResponse clientResponse = HandlerAdapter.getInstance().invoke(httpMessage);
        String expected = clientResponse.getBody().toString();

        Assertions.assertThat("getUserTest").isEqualTo(expected);
    }

    @DisplayName("요청 실려온 queryParameter를 handler의 parameter로 컨버팅 하는지 검증")
    @Test
    void invokeParameterTest() throws IOException, InvocationTargetException, IllegalAccessException {
        List<String> httpMessageData = List.of("GET /user/create?userId=fistkim101&password=1234&name=leo&email=fistkim101%40gmail.com HTTP/1.1");
        HttpMessage httpMessage = new HttpMessage(httpMessageData);

        User actual = (User) HandlerAdapter.getInstance().invoke(httpMessage).getBody();

        Assertions.assertThat(actual.getUserId()).isEqualTo("fistkim101");
        Assertions.assertThat(actual.getEmail()).isEqualTo("fistkim101%40gmail.com");
    }

}
