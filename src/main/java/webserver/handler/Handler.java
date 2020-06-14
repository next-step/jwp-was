package webserver.handler;

import http.RequestMessage;
import http.ResponseMessage;


import java.io.IOException;

public interface Handler {

    default void handle(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException {
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

    void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException;

    void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException;

}
