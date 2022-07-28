package webserver.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.List;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandlerMapperTest {
    @Test
    @DisplayName("유저 생성 요청이 주어졌을 때 user controller로 잘 요청한다.")
    void testUserControllerMapping() throws IOException, URISyntaxException {
        HttpRequest httpRequest = new HttpRequest(
                List.of(("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Accept: */*").split("\n"))
        );
        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");


        byte[] result = new HandlerMapper().handle(httpRequest);

        assertThat(result).containsExactly(convertObjectToByteArray(user));
    }

    private byte[] convertObjectToByteArray(Object input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(input);

        return bos.toByteArray();
    }
}
