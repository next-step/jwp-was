package webserver;

import db.DataBase;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.handler.CreateUserController;
import webserver.handler.LoginController;
import webserver.request.ContentType;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.request.RequestHeaders;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;
import webserver.template.HandleBarTemplateLoader;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            final DataOutputStream dos = new DataOutputStream(out);

            final RequestLine requestLine = RequestLine.parse(bufferedReader.readLine());
            final RequestHeaders requestHeaders = new RequestHeaders();
            addAllRequestHeaders(bufferedReader, requestHeaders);
            final RequestBody requestBody = getRequestBody(bufferedReader, requestHeaders);

            if (requestForCreateUser(requestLine)) {
                final HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, requestBody);
                final CreateUserController createUserController = new CreateUserController();
                final HttpResponse response = createUserController.handle(httpRequest);

                response.write(dos);
                return;
            }

            if (requestForLogin(requestLine)) {
                final HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, requestBody);
                final LoginController createUserController = new LoginController();
                final HttpResponse response = createUserController.handle(httpRequest);

                response.write(dos);
                return;
            }

            if (requestForUserList(requestLine)) {
                userList(requestHeaders, dos);
                return;
            }

            String fileExtension = FileIoUtils.getFileExtension(requestLine.getLocation());
            final String contentType = ContentType.of(fileExtension).getMediaType();
            String filePath = getFilePath(fileExtension);

            final byte[] body = FileIoUtils.loadFileFromClasspath(filePath + requestLine.getLocation());

            responseOk(dos, body, contentType);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String getFilePath(final String fileExtension) {
        if (fileExtension.endsWith("html") || fileExtension.endsWith("ico")) {
            return "templates";
        }
        return "static";
    }

    private void userList(final RequestHeaders requestHeaders, final DataOutputStream dos) throws IOException {
        if (!isLogined(requestHeaders)) {
            response302Header(dos, "/user/login.html");
            return;
        }

        final Collection<User> users = DataBase.findAll();
        final Map<String, Collection<User>> params = Map.of("users", users);

        final String load = HandleBarTemplateLoader.load("user/list", params);
        final byte[] body = load.getBytes(StandardCharsets.UTF_8);

        responseOk(dos, body, ContentType.HTML.getMediaType());
    }

    private void responseOk(final DataOutputStream dos, final byte[] body, final String contentType) {
        response200Header(dos, body.length, contentType);
        responseBody(dos, body);
    }

    private boolean isLogined(final RequestHeaders requestHeaders) {
        return Optional.ofNullable(requestHeaders.get("Cookie"))
            .map(logined -> logined.equals("logined=true"))
            .orElse(false);
    }

    private boolean requestForUserList(final RequestLine requestLine) {
        return requestLine.isGet() && requestLine.getLocation().equals("/user/list.html");
    }

    private void addAllRequestHeaders(final BufferedReader bufferedReader, final RequestHeaders requestHeaders) throws IOException {
        String header = bufferedReader.readLine();
        while (!header.isEmpty()) {
            logger.debug("header : {}", header);
            requestHeaders.add(header);
            header = bufferedReader.readLine();
        }
    }

    private RequestBody getRequestBody(final BufferedReader bufferedReader, final RequestHeaders requestHeaders) throws IOException {
        if (requestHeaders.hasRequestBody()) {
            final String body = IOUtils.readData(bufferedReader, requestHeaders.getContentLength());
            logger.debug("body : {}", body);
            return new RequestBody(body);
        }
        return RequestBody.EMPTY_REQUEST_BODY;
    }

    private boolean requestForLogin(final RequestLine requestLine) {
        return requestLine.isPost() && requestLine.getLocation().equals("/user/login");
    }

    private boolean requestForCreateUser(final RequestLine requestLine) {
        return requestLine.isPost() && requestLine.getLocation().equals("/user/create");
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, final String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, final String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
