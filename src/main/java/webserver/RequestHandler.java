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
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.request.RequestBody;
import webserver.request.RequestHeaders;
import webserver.request.RequestLine;
import webserver.request.UserBinder;

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

            String header = bufferedReader.readLine();
            while (!header.isEmpty()) {
                logger.debug("header : {}", header);
                requestHeaders.add(header);
                header = bufferedReader.readLine();
            }

            if (requestHeaders.hasRequestBody()) {
                final String body = IOUtils.readData(bufferedReader, requestHeaders.getContentLength());
                logger.debug("body : {}", body);

                RequestBody requestBody = new RequestBody(body);
                createUser(requestLine, requestBody, dos);
            }

            final byte[] body = FileIoUtils.loadFileFromClasspath("templates" + requestLine.getLocation());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void createUser(final RequestLine requestLine, final RequestBody requestBody, final DataOutputStream dos) {
        if (requestForCreateUser(requestLine)) {
            User user = UserBinder.from(requestBody.getParameters());
            logger.debug("user = {}", user);

            DataBase.addUser(user);
            response302Header(dos);
        }
    }

    private boolean requestForCreateUser(final RequestLine requestLine) {
        return requestLine.isPost() && requestLine.getLocation().equals("/user/create");
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
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
