package webserver.http;

import webserver.domain.HttpEntity;

public abstract class AbstractControllerCreator implements ControllerCreator{

    @Override
    public void doMethodCall(HttpRequest httpRequest) {
        String method = httpRequest.getMethod();
        if(method.equals("GET")){
            doGet(httpRequest);
        }

        if(method.equals("POST")){
            doPost(httpRequest);
        }
    }
}
