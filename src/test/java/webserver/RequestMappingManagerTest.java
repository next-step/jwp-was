package webserver;

import controller.Controller;
import http.request.HttpRequest;
import mock.MockSocket;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void testClass() throws Exception {
        Class controllerClass = Class.forName("controller.LoginController");
        Controller controller = (Controller) controllerClass.newInstance();
        assertThat(controller.getPath()).isEqualTo("/user/login");
    }

    @Test
    void getPathFile() throws Exception {
        String packageName = "controller";
        File dir = new File("src/main/java/" + packageName.replace(".", "/"));

        System.out.println(dir.getAbsolutePath());

        for (File file : dir.listFiles()) {
            System.out.println(file.getName().replace(".java", ""));
        }

    }
}
