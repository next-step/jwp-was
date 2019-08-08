package webserver.http;


import webserver.domain.HttpResponseEntity;

public abstract class AbstractControllerCreator implements ControllerCreator{

    @Override
    public HttpResponseEntity doMethodCall(HttpRequest httpRequest) {
        String method = httpRequest.getMethod();
        if(method.equals("GET")){
            return doGet(httpRequest);
        }

        if(method.equals("POST")){
            return doPost(httpRequest);
        }

        return HttpResponseEntity.setStatusResponse(httpRequest,
                HttpStatus.NOT_FOUND);
    }
}
