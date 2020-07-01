package http;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class DefaultController implements Controller {

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        if (request.isGet()) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    void doGet(final HttpRequest request, final HttpResponse response) {
        System.out.println("DefaultController doGet");
        response.buildResponseLine(HttpStatus.BAD_REQUEST);
        response.setContentType("test/html; charset=utf-8");
        response.setContentLength("/user/form.html");
        try {
            response.setResponseBody("/user/form.html");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        response.print();
    }

    void doPost(final HttpRequest request, final HttpResponse response) {
        System.out.println("DefaultController doPost");
    }

}
