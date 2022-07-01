package mvc;

import mvc.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.WasBaseTest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class MvcTest extends WasBaseTest {
    private RequestMapping requestMapping;

    @BeforeEach
    void setUp() throws Exception {

        requestMapping = new FakeRequestMapping();
    }

    @Test
    void service() throws Exception {
        HttpRequest request = request("http/HTTP_GET_without_QueryString.txt");
        HttpResponse response = new HttpResponse(createOutputStream("users.html"));
        Controller controller = requestMapping.getController(request);
        controller.service(request, response);
    }
}
