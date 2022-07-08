package webserver.http;

public abstract class RequestAbstractController implements RequestController {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse response) throws Exception {
        if (httpRequest.getMethod() == HttpMethod.POST) {
            doPost(httpRequest, response);
            return;
        }

        doGet(httpRequest, response);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse response) throws Exception {
        response.notfound();
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse response) throws Exception {
        response.notfound();
    }
}
