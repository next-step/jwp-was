package utils;

import model.ClientResponse;
import model.HttpRequestMessage;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.RequestService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class HandlerAdapterTest {

    @DisplayName("요청에 대해 적절한 handler를 찾아 invoke 하는지 검증")
    @Test
    void invokeTest() throws IOException, InvocationTargetException, IllegalAccessException {
        List<String> httpMessageData = List.of("GET /user HTTP/1.1");
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(httpMessageData);

        ClientResponse clientResponse = HandlerAdapter.getInstance().invoke(httpRequestMessage);

        Assertions.assertThat(clientResponse.getBytesBody()).isEqualTo(RequestService.bodyToBytes("getUserTest"));
    }

    @DisplayName("요청 실려온 queryParameter를 handler의 parameter로 컨버팅 하는지 검증")
    @Test
    void invokeParameterTest() throws IOException, InvocationTargetException, IllegalAccessException {
        List<String> httpMessageData = List.of("GET /user/create?userId=fistkim101&password=1234&name=kim&email=fistkim101%40gmail.com HTTP/1.1");
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(httpMessageData);

        User actual = new User("fistkim101", "1234", "kim", "fistkim101%40gmail.com");
        byte[] expected = HandlerAdapter.getInstance().invoke(httpRequestMessage).getBytesBody();

        Assertions.assertThat(RequestService.bodyToBytes(actual)).isEqualTo(expected);
    }

}
