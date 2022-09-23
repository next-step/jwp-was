package webserver.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;

public class StaticForwardController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(StaticForwardController.class);

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        String path = "./static" + httpRequest.path();
        if (httpRequest.path().endsWith("css")) {
            httpResponse.addHeader("Content-Type", "text/css");
            httpResponse.staticForward(path);
            return;
        }

        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponse.staticForward(path);
    }
}
