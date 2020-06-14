package webserver.handler;

import http.RequestMessage;
import http.ResponseMessage;

public interface Handler {

    default void handle(RequestMessage requestMessage, ResponseMessage responseMessage) {
        switch (requestMessage.getMethod()) {
            case GET:
                doGet(requestMessage, responseMessage);
            case POST:
                doPost(requestMessage, responseMessage);
            default:
                throw new IllegalArgumentException("Unsupported Method.");
        }
    }

    void doGet(RequestMessage requestMessage, ResponseMessage responseMessage);

    void doPost(RequestMessage requestMessage, ResponseMessage responseMessage);

}
