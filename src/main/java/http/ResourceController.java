package http;

public class ResourceController extends DefaultController {

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        super.handle(request, response);
    }

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) {
        response.buildResponseLine(HttpStatus.OK);
        response.setCharset("utf-8");
        response.setResponseBody(request.getPath());
        response.print();
    }

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        super.doPost(request, response);
    }
}
