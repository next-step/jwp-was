package webserver.http;

public abstract class AbstractControllerStructor implements ControllerStructor{

    @Override
    public HttpResponse doMethodCall(HttpRequest request) {
        String method = request.getMethod();
        if(method.equals("GET")){
            return doGet(request);
        }

        if(method.equals("POST")){
            return doPost(request);
        }

        return HttpResponse.pageNotFound(request);
    }

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        return HttpResponse.pageNotFound(httpRequest);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        return HttpResponse.pageNotFound(httpRequest);
    }
}
