package handler;

import model.request.HttpRequestMessage;
import model.response.HttpResponseMessage;
import model.response.ResponseLine;

public abstract class AbstractHandler implements PathHandler {
    public abstract Boolean canHandling(HttpRequestMessage httpRequestMessage);

    public abstract HttpResponseMessage handle(HttpRequestMessage httpRequestMessage);

    private HttpResponseMessage doGet() {
        return new HttpResponseMessage(ResponseLine.httpBadRequest(), null, new byte[0]);
    }

    private HttpResponseMessage doPost() {
        return new HttpResponseMessage(ResponseLine.httpBadRequest(), null, new byte[0]);
    }
}
