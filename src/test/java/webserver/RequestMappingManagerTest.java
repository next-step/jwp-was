package webserver;

import mock.MockSocket;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class RequestMappingManagerTest {

    @Test
    void fileLoadFromPath() {
        String request = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*";
        MockSocket socket = new MockSocket(request);

        InputStream in = socket.getInputStream();

        HttpRequest httpRequest = HttpRequestReader.read(in);

        //리쿼스트 path를 판단
//        HttpResponse httpResponse = RequestMappingManager.execute(httpRequest);

    }
}
