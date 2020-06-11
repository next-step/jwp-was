package http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.ui.UserController;
import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class RequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandler.class);

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;
    private boolean isLogined = false;

    public RequestMappingHandler(final BufferedReader bufferedReader, final DataOutputStream dataOutputStream) throws IOException {
        this.httpRequest = new HttpRequest(bufferedReader);
        this.httpResponse = new HttpResponse(dataOutputStream);
        run();
    }

    private void run() {
        String path = httpRequest.getPath();

        if (path.matches("/user/.*")) {
            if (path.matches("/user/list.*")) {
                middleware();
            }
        }

        if (path.matches("/css/.*")) {
            httpResponse.forward(path);
        }

        handlerByUserController();
    }

    private void handlerByUserController() {
        String method = httpRequest.getMethod();
        String path = httpRequest.getPath();

        if (method.equals("GET")) {
            ResponseObject responseObject = UserController.mappingByGetMethod(path);
            if (path.equals("/user/login")) {
                if (responseObject.getCode() == 200) {
                    httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
                    httpResponse.sendRedirect("/index.html");
                }
            }

            if (path.equals("/user/list")) {
                byte[] body = viewListByTemplate(responseObject);
                httpResponse.response200Header(body.length);
                httpResponse.responseBody(body);
            }

            handlerGetMethod(responseObject);
            return;
        }

        ResponseObject responseObject = UserController.mappingByPostMethod(path, httpRequest.getRequestParameters());
        handlerPostMethod(responseObject, path);
    }

    private void middleware() {
        if (!isLogined) {
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login.html");
        }
    }

    private void handlerGetMethod(ResponseObject responseObject) {
        byte[] body = getForm(responseObject.getRequestPath());
        httpResponse.response200Header(body.length);
        httpResponse.responseBody(body);
    }

    private void handlerPostMethod(ResponseObject responseObject, String path) {
        if (path.equals("/user/login")) {
            if (responseObject.getCode() == 200) {
                httpResponse.addHeader("Set-Cookie", "logined=true; Path=/");
                httpResponse.sendRedirect("/index.html");
            }
            httpResponse.addHeader("Set-Cookie", "logined=false; Path=/");
            httpResponse.sendRedirect("/user/login.html");
        }

        if (path.equals("/user/create")) {
            if (responseObject.getCode() == 302) {
                httpResponse.sendRedirect(responseObject.getLocation());
            }
        }
    }

    private byte[] getForm(String filePath) {
        try {
            return viewByUserForm(filePath);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new IllegalArgumentException("Not Found Path");
        }
    }

    private byte[] viewByUserForm(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
    }

    private byte[] viewListByTemplate(ResponseObject responseObject) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        Template template = null;
        try {
            template = handlebars.compile(responseObject.getRequestPath());
            List<User> users = (List<User>) responseObject.getData();
            String listPage = template.apply(users);
            return listPage.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("template error");
        }
    }

}
