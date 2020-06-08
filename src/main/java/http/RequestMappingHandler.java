package http;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.ui.UserController;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class RequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final BufferedReader bufferedReader;
    private final Response response;

    public RequestMappingHandler(final BufferedReader bufferedReader, final DataOutputStream dataOutputStream) {
        this.bufferedReader = bufferedReader;
        this.response = new Response(dataOutputStream);
    }

    public void read() throws IOException {
        String readLine = bufferedReader.readLine();
        String firstLine = readLine;
        int contentLength = 0;
        logger.debug(readLine);

        while (!"".equals(readLine)) {
            readLine = bufferedReader.readLine();
            if (readLine.matches("Content-Length:.*")) {
                String[] lengths = readLine.split(":");
                contentLength = Integer.valueOf(lengths[1].trim());
            }
            logger.debug(readLine);
        }

        String requestBody = IOUtils.readData(bufferedReader, contentLength);
        if (Strings.isBlank(requestBody)) {
            handler(firstLine);
            return;
        }
        handler(firstLine, requestBody);

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
            byte[] body = getUserForm("./templates/index.html");
            response.response200Header(body.length);
            response.responseBody(body);
        }

        if (path.matches("/user/.*")) {
            handlerByUserController(requestLine);
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
                    return;
                }
            }
            handlerGetMethod(responseObject);
            return;
        }
        handlerPostMethod(responseObject, requestLine.getPath());
    }

    private void handlerGetMethod(ResponseObject responseObject) {
        byte[] body = getUserForm(responseObject.getRequestPath());
        response.response200Header(body.length);
        response.responseBody(body);
    }

    private void handlerPostMethod(ResponseObject responseObject, String path) {
        if (path.equals("/user/login")) {
            if (responseObject.getCode() == 200) {
                response.responseHeaderByLoginSuccess();
                return;
            }
            response.response302HeaderByLoginFail(responseObject.getLocation());
            return;
        }

        if (path.equals("/user/create")) {
            if (responseObject.getCode() == 302) {
                response.response302Header(responseObject.getLocation());
            }
        }
    }

    private byte[] getUserForm(String filePath) {
        try {
            return viewByUserForm(filePath);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new IllegalArgumentException("Not Found Path");
        }
    }

    private byte[] viewByUserForm(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(path);
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
