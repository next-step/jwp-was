package webserver.controller;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatusCode;
import webserver.http.ViewResolver;

public class AbstractController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            doService(httpRequest, httpResponse);
        } catch (HttpException e) {
            logger.warn("## Http Exception {}", e.getMessage());
            ViewResolver.from(httpRequest, httpResponse).error(e.getHttpStatusCode());
        }
    }

    private void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        switch (httpRequest.getMethod()) {
            case GET:
                doGet(httpRequest, httpResponse);
                break;
            case POST:
                setUserService(UserService.getInstance());
                doPost(httpRequest, httpResponse);
                break;
            default:
                throw new HttpException(HttpStatusCode.BAD_REQUEST);
        }
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
    }

    protected void setUserService(UserService userService) {

    }

}
