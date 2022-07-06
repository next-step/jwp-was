package mvc;

import mvc.controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import was.WasTestTemplate;
import was.http.HttpRequest;
import was.http.HttpResponse;

public class MvcTest {
    private WasTestTemplate testTemplate;

    private RequestMapping requestMapping;

    @BeforeEach
    void setUp() throws Exception {
        testTemplate = new WasTestTemplate();
        requestMapping = new FakeRequestMapping();
    }

    @Test
    void service() throws Exception {
        HttpRequest request = testTemplate.request("http/HTTP_GET_without_QueryString.txt");
        HttpResponse response = new HttpResponse(testTemplate.createOutputStream("users.html"));
        Controller controller = requestMapping.getController(request);
        controller.service(request, response);
    }
}
