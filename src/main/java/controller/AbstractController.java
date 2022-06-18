package controller;

import java.io.IOException;
import webserver.request.Request;
import webserver.response.Response;
import webserver.response.ResponseFactory;

public abstract class AbstractController implements Controller {
    @Override
    public Response service(Request request) throws IOException {
        return request.isGet() ? doGet(request)
                : request.isPost() ? doPost(request)
                : ResponseFactory.createNotImplemented();
    }

    abstract Response doGet(Request request) throws IOException;

    abstract Response doPost(Request request) throws IOException;
}
