package http;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.header.RequestHeader;
import model.User;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.ui.UserController;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RequestMappingHandler {
    private static final int MIN_CONTENT_LENGTH = 0;
    private static final String END_OF_LINE = "";

    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandler.class);

    private final BufferedReader bufferedReader;
    private final Response response;
    private RequestHeader requestHeader;
    private boolean isLogined = false;

    public RequestMappingHandler(final BufferedReader bufferedReader, final DataOutputStream dataOutputStream) throws IOException {
        this.bufferedReader = bufferedReader;
        this.response = new Response(dataOutputStream);
        read();
    }

    private void read() throws IOException {
        String readLine = bufferedReader.readLine();
        String firstLine = readLine;
        int contentLength = MIN_CONTENT_LENGTH;
        logger.debug(readLine);

        while (!END_OF_LINE.equals(readLine)) {
            readLine = bufferedReader.readLine();
            requestHeader = new RequestHeader(readLine);
            isLogined = requestHeader.isLogined();
            contentLength = Math.max(contentLength, requestHeader.getContentLength());

            logger.debug(readLine);
        }
        if (contentLength > 0) {
            String requestBody = IOUtils.readData(bufferedReader, contentLength);
            handler(firstLine, requestBody);
            return;
        }
        handler(firstLine);
    }

    private void handler(String readLine, String requestBody) {
        RequestLine requestLine = RequestLineParser.parse(readLine, requestBody);
        String path = requestLine.getPath();
        if (path.matches("/user/.*")) {
            handlerByUserController(requestLine);
        }
    }

    private void handler(String readLine) {
        RequestLine requestLine = RequestLineParser.parse(readLine);
        String path = requestLine.getPath();

        if (path.equals("/index.html")) {
            byte[] body = getForm("./templates/index.html");
            response.response200Header(body.length);
            response.responseBody(body);
        }

        if (path.matches("/user/.*")) {
            if (path.matches("/user/list.*")) {
                middleware();
            }
            handlerByUserController(requestLine);
        }

        if (path.matches("/css/.*")) {
            byte[] body = getForm("./static/" + path);
            response.responseHeaderByCss();
            response.responseBody(body);
        }
    }

    private void handlerByUserController(RequestLine requestLine) {
        ResponseObject responseObject = new UserController(requestLine).mapping();
        String method = requestLine.getMethodName();
        String path = requestLine.getPath();
        if (method.equals("GET")) {
            if (path.equals("/user/login")) {
                if (responseObject.getCode() == 200) {
                    response.responseHeaderByLoginSuccess();
                }
            }

            if (path.equals("/user/list")) {
                byte[] body = viewListByTemplate(responseObject);
                response.response200Header(body.length);
                response.responseBody(body);
            }

            handlerGetMethod(responseObject);
        }
        handlerPostMethod(responseObject, requestLine.getPath());
    }

    private void middleware() {
        if (!isLogined) {
            response.response302Header("/user/login.html");
        }
    }

    private void handlerGetMethod(ResponseObject responseObject) {
        byte[] body = getForm(responseObject.getRequestPath());
        response.response200Header(body.length);
        response.responseBody(body);
    }

    private void handlerPostMethod(ResponseObject responseObject, String path) {
        if (path.equals("/user/login")) {
            if (responseObject.getCode() == 200) {
                response.responseHeaderByLoginSuccess();
            }
            response.response302HeaderByLoginFail(responseObject.getLocation());
        }

        if (path.equals("/user/create")) {
            if (responseObject.getCode() == 302) {
                response.response302Header(responseObject.getLocation());
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestMappingHandler that = (RequestMappingHandler) o;
        return Objects.equals(bufferedReader, that.bufferedReader) &&
                Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bufferedReader, response);
    }
}
