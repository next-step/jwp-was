package webserver.http;

public abstract class RequestAbstractController implements RequestController {
    @Override
    public HttpResponse service(HttpRequest httpRequest) throws Exception {
        if (httpRequest.getMethod() == HttpMethod.POST) {
            return doPost(httpRequest);
        }

        return doGet(httpRequest);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        return new HttpResponse(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) throws Exception {
        return new HttpResponse(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }
}
