package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import http.parsers.RequestContextParser;
import http.requests.RequestContext;
import model.User;
import model.UserParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TemplateReader;

/**
 * TODO: 이 친구는 다음과 같이 리팩토링이 필요함 (나중)
 * 이름은 RequestHandler지만 요청과 응답을 다 받고 있음.
 * 1. RequestHandler -> Dispatcher [rename]
 * 2. request, response 핸들러는 따로 만드는 방향이 맞을 듯
 * 3. request, response는 context로 추상화 하는 방향으로 (scope에 따라 유연하게 다룰 수 있게)
 */
public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            final RequestContext requestContext = RequestContextParser.parse(in);
            logger.debug("request context: {}", requestContext);

            if ("/user/create".equals(requestContext.getPath())) {
                final User user = UserParser.parse(requestContext);
                logger.debug("user: {}", user);
            }

            DataOutputStream dos = new DataOutputStream(out);
            final byte[] body = convertFileToByte(requestContext.getPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] convertFileToByte(String path) {
        try {
            return TemplateReader.read(path);
        } catch (FileNotFoundException e) {
            return "Hello World".getBytes();
        }
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
