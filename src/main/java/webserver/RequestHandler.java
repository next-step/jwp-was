package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.domain.RequestBody;
import webserver.http.domain.RequestHeader;
import webserver.http.domain.RequestLine;
import webserver.http.enums.HTTPMethod;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final byte[] HELLO_WORLD = "Hello World".getBytes();

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            RequestLine requestLine = new RequestLine(line);

            RequestHeader requestHeader = new RequestHeader();
            while (StringUtils.hasText(line)) {
                line = br.readLine();
                requestHeader.addHeader(line);
            }

            byte[] body = checkIndex(requestLine);
            body = checkSignUpForm(body, requestLine);
            checkSignUp(requestLine, br, requestHeader);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] checkIndex(RequestLine requestLine) throws IOException, URISyntaxException {
        if (requestLine.path().equals("/index.html")) {
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        }
        return HELLO_WORLD;
    }

    private byte[] checkSignUpForm(byte[] body, RequestLine requestLine) throws IOException, URISyntaxException {
        if (requestLine.path().equals("/user/form.html")) {
            return FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        }
        return body;
    }

    private void checkSignUp(RequestLine requestLine, BufferedReader br, RequestHeader requestHeader) throws IOException, URISyntaxException {
        if (requestLine.method().equals(HTTPMethod.POST) && requestLine.samePath("/user/create")) {
            String body = IOUtils.readData(br, Integer.parseInt(requestHeader.getValue("Content-Length")));
            RequestBody requestBody = new RequestBody(body);
            Map<String, String> bodies = requestBody.bodies();
            String userId = bodies.get("userId");

            DataBase.addUser(new User(userId, bodies.get("password"), bodies.get("name"), bodies.get("email")));

            logger.debug(DataBase.findUserById(userId).toString());
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
