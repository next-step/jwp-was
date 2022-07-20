package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
            connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            RequestLine requestLine = new RequestLine(reader.readLine());
            HttpHeaders headers = HttpHeaders.from(reader);

            String requestBody = IOUtils.readData(reader, headers.getContentLength());
            byte[] body = getBody(requestLine, requestBody);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getBody(RequestLine requestLine, String requestBody) throws IOException, URISyntaxException {
        String path = requestLine.getRequestPath();

        if (path.startsWith("/index.html")) {
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        }

        if (path.startsWith("/user/form.html")) {
            return FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        }

        if (path.startsWith("/user/create")) {
            Parameters parameters = new Parameters(requestBody);
            String userId = parameters.getParameter("userId");
            String password = parameters.getParameter("password");
            String name = parameters.getParameter("name");
            String email = parameters.getParameter("email");

            User user = new User(userId, password, name, email);
            DataBase.addUser(user);
        }

        return new byte[0];
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
