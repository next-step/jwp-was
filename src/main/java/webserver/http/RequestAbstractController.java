package webserver.http;

public abstract class RequestAbstractController implements RequestController {
    @Override
    public HttpResponse service(Request request) throws Exception {
        if (request.getMethod() == HttpMethod.POST) {
            return doPost(request);
        }

        return doGet(request);
    }

    @Override
    public HttpResponse doGet(Request request) throws Exception {
        return new HttpResponse(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }

    @Override
    public HttpResponse doPost(Request request) throws Exception {
        return new HttpResponse(HttpStatus.NOT_FOUND, MediaType.TEXT_HTML_UTF8, "", null);
    }
}
