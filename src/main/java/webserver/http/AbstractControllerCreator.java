package webserver.http;

public abstract class AbstractControllerCreator implements ControllerCreator{

    @Override
    public HttpResponse doMethodCall(HttpRequest request) {
        String method = request.getMethod();
        if(method.equals("GET")){
            return doGet(request);
        }

        if(method.equals("POST")){
            return doPost(request);
        }

        return HttpResponse.setStatusResponse(HttpStatus.NOT_FOUND);
    }
}
