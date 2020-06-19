package handler;

import http.request.RequestMessage;
import http.response.ResponseMessage;


import java.io.IOException;

public interface Handler {

    void service(RequestMessage requestMessage, ResponseMessage responseMessage) throws IOException;
}
