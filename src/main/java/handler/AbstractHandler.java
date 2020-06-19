package handler;

import http.request.RequestMessage;
import http.response.ResponseMessage;


import java.io.IOException;

public abstract class AbstractHandler implements Handler{

    public void service(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {
        switch (requestMessage.getMethod()) {
            case GET:
                doGet(requestMessage, responseMessage);
                break;
            case POST:
                doPost(requestMessage, responseMessage);
                break;
            default:
                throw new IllegalArgumentException("Unsupported Method.");
        }
    }

    abstract void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException;

    abstract void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException;

}
