package webserver;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.domain.RequestBody;
import webserver.http.domain.RequestHeader;
import webserver.http.domain.RequestUrl;

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
            String url = br.readLine();
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.addRequestHeaders(url, br);

            RequestUrl requestUrl = new RequestUrl(url);
            byte[] body = checkIndex(requestUrl);
            body = checkSignUpForm(body, requestUrl);
            checkSignUp(requestUrl, br, requestHeader);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] checkIndex(RequestUrl requestUrl) throws IOException, URISyntaxException {
        if (requestUrl.isGetMethod() && requestUrl.samePath("/index.html")) {
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        }
        return HELLO_WORLD;
    }

    private byte[] checkSignUpForm(byte[] body, RequestUrl requestUrl) throws IOException, URISyntaxException {
        if (requestUrl.isGetMethod() && requestUrl.samePath("/user/form.html")) {
            return FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        }
        return body;
    }

    private void checkSignUp(RequestUrl requestUrl, BufferedReader br, RequestHeader requestHeader) throws IOException, URISyntaxException {
        if (requestUrl.isPostMethod() && requestUrl.samePath("/user/create")) {
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
