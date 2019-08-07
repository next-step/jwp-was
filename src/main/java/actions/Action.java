package actions;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Action {
    public HttpResponse execute(HttpRequest request) throws Exception;
}
