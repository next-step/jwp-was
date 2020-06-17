package webserver.handler;

import http.RequestMessage;
import http.ResponseMessage;


import java.io.IOException;

public interface Handler {

    void service(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException;
}
