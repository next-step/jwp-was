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

import javax.xml.crypto.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String BLANK_STRING = "";

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

            String line = reader.readLine();
            RequestLine requestLine = new RequestLine(line);

            while (line != null && !BLANK_STRING.equals(line)) {
                logger.info("header = {}", line);
                line = reader.readLine();
            }

            byte[] body = getBody(requestLine);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private byte[] getBody(RequestLine requestLine) throws IOException, URISyntaxException {
        String path = requestLine.getRequestPath();

        if (path.startsWith("/index.html")) {
            return FileIoUtils.loadFileFromClasspath("./templates/index.html");
        }

        if (path.startsWith("/user/form.html")) {
            return FileIoUtils.loadFileFromClasspath("./templates/user/form.html");
        }

        if (path.startsWith("/user/create")) {
            String userId = requestLine.getParameter("userId");
            String password = requestLine.getParameter("password");
            String name = requestLine.getParameter("name");
            String email = requestLine.getParameter("email");

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
