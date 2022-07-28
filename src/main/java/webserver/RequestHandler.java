package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.handler.CreateUserController;
import webserver.handler.LoginController;
import webserver.handler.UserListController;
import webserver.request.ContentType;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;
import webserver.request.RequestHeaders;
import webserver.request.RequestLine;
import webserver.response.HttpResponse;

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
                final HttpRequest httpRequest = new HttpRequest(requestLine, requestHeaders, requestBody);
                final UserListController createUserController = new UserListController();
                final HttpResponse response = createUserController.handle(httpRequest);

                response.write(dos);
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

    private void responseOk(final DataOutputStream dos, final byte[] body, final String contentType) {
        response200Header(dos, body.length, contentType);
        responseBody(dos, body);
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
