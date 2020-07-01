package http;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends DefaultController {

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        super.handle(request, response);
    }

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) {
        System.out.println("This is ResourceController doGet : requestPath= " + request.getPath());
        try {
            byte[] bytes = FileIoUtils.loadFileFromClasspath(STATIC_PREFIX + request.getPath());
            if (bytes == null) {
                super.doGet(request, response);
            }

            response.buildResponseLine(HttpStatus.OK);
            response.setContentType("text/html; charset=utf-8");
            response.setResponseBody(request.getPath());
            response.print();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }


    }

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        System.out.println("This is ResourceController doPost");

    }
}
