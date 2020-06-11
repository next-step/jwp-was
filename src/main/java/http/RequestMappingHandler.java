package http;

import controller.BaseController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.ui.CreateUserController;
import user.ui.ListUserController;
import user.ui.LoginController;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class RequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandler.class);

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;
    private BaseController baseController;

    public RequestMappingHandler(final BufferedReader bufferedReader, final DataOutputStream dataOutputStream) throws IOException {
        this.httpRequest = new HttpRequest(bufferedReader);
        this.httpResponse = new HttpResponse(dataOutputStream);
        run();
    }

    private void run() {
        String path = httpRequest.getPath();
        if (path.matches("/css/.*") || path.matches("/fonts/.*") || path.matches("/js/.*") || path.matches("/images/*")) {
            httpResponse.forward(path);
            return;
        }
        handlerByUserController();
    }

    private void handlerByUserController() {
        String method = httpRequest.getMethod();
        String path = httpRequest.getPath();

        if (method.equals("GET")) {
            if (path.equals("/user/list")) {
                baseController = new ListUserController();
                if (!httpRequest.isLogin()) {
                    httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
                    httpResponse.sendRedirect("/user/login.html");
                    return;
                }
                HttpResponse response = baseController.doGet(httpRequest, httpResponse);
                response.forwardTemplateBody();
                return;
            }

            httpResponse.forwardBody(path);
            return;
        }
        postMethod();
    }

    private void postMethod() {
        String path = httpRequest.getPath();
        if (path.equals("/user/create")) {
            baseController = new CreateUserController();
            baseController.doPost(httpRequest, httpResponse);
        }

        if (path.equals("/user/login")) {
            baseController = new LoginController();
            baseController.doPost(httpRequest, httpResponse);
        }
    }
}
