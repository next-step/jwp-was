package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.HttpMessage;
import model.HttpMessageData;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

class HandlerAdapterTest {

    @DisplayName("요청에 대해 적절한 handler를 찾아 invoke 하는지 검증")
    @Test
    void invokeTest() throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        List<String> httpMessageData = List.of("GET /user HTTP/1.1");
        HttpMessage httpMessage = new HttpMessage(new HttpMessageData(httpMessageData));
        String expected = (String) HandlerAdapter.getInstance().invoke(httpMessage);

        Assertions.assertThat("getUserReturnValue").isEqualTo(expected);
    }

    @DisplayName("요청 실려온 queryParameter를 handler의 parameter로 컨버팅 하는지 검증")
    @Test
    void invokeParameterTest() throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        List<String> httpMessageData = List.of("GET /user/create?userId=fistkim101&password=1234&name=leo&email=fistkim101%40gmail.com HTTP/1.1");
        HttpMessage httpMessage = new HttpMessage(new HttpMessageData(httpMessageData));

        User actual = (User) HandlerAdapter.getInstance().invoke(httpMessage);

        Assertions.assertThat(actual.getUserId()).isEqualTo("fistkim101");
        Assertions.assertThat(actual.getEmail()).isEqualTo("fistkim101%40gmail.com");
    }
}
