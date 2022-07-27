package webserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public abstract class AbstractController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);


    public static final String INDEX_URl = "index.html";
    public static final String LOGIN_FAIL_URl = "/user/login_failed.html";
    public static final String LOGINED_KEY = "logined";

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (request.isPostMethod()) {
            doPost(request, response);
        } else {
            doGet(request, response);
        }
    }

    public abstract void doPost(HttpRequest request, HttpResponse response);

    public abstract void doGet(HttpRequest request, HttpResponse response);

}
