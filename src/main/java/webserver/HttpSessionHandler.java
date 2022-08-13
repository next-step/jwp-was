package webserver;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpSession;

public interface HttpSessionHandler {

    HttpSession getHttpSession(HttpRequest httpRequest, HttpResponse httpResponse);

}
