package webserver;

import db.DataBase;
import http.*;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = br.readLine();
            logger.debug("Request Line :: {}", line);
            RequestLine requestLine = RequestLineParser.parse(line);

            int contentLength = 0;
            while (!line.equals("")) {
                line = br.readLine();
                logger.debug("Header :: {}", line);

                if (line.split(":")[0].contains("Content-Length")) {
                    String s = line;
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            String requestBody = IOUtils.readData(br, contentLength);
            logger.debug("Body :: {}", line);

            HttpMethod httpMethod = requestLine.getHttpMethod();
            String path = requestLine.getPath();

            if (isPost(httpMethod) && "/user/create".equals(path)) {
                FormData formData = new FormData(requestBody);
                String userId = formData.getValue("userId");
                String password = formData.getValue("password");
                String name = formData.getValue("name");
                String email = formData.getValue("email");

                DataBase.addUser(new User(userId, password, name, email));
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = null;
                response302Header(dos, "/index.html");
                responseBody(dos, body);
            } else if (isPost(httpMethod) && "/user/login".equals(path)) {
                FormData formData = new FormData(requestBody);
                String userId = formData.getValue("userId");
                String password = formData.getValue("password");

                User user = DataBase.findUserById(userId);
                if (user.getPassword() != null && user.getPassword().equals(password)) {
                    DataOutputStream dos = new DataOutputStream(out);
                    response302HeaderWithCookies(dos, "/index.html", "logined", "true");
                } else {
                    DataOutputStream dos = new DataOutputStream(out);
                    response302HeaderWithCookies(dos, "/user/login_failed.html", "logined", "false");
                }
            } else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = FileIoUtils.loadFileFromClasspath("./templates/" + path);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isPost(HttpMethod httpMethod) {
        return HttpMethod.POST.equals(httpMethod);
    }

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
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

    private void response302HeaderWithCookies(DataOutputStream dos, String location, String cookieName, String cookieValue) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            dos.writeBytes("Set-Cookie: " + cookieName + ": " + cookieValue + "; Path=/\r\n");
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
