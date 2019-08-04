package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import utils.StringUtils;
import webserver.http.RequestBody;
import webserver.http.RequestHeaders;
import webserver.http.RequestLine;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final String INDEX_HTML = "/index.html";
    public static final String TEMPLATES_PATH = "templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();

            RequestLine requestLine = RequestLine.parse(line);
            RequestHeaders requestHeaders = new RequestHeaders();
            while (!"".equals(line = br.readLine())) {
                if (null == line) break;
                requestHeaders.add(line);
            }

            RequestBody requestBody = null;
            if ("POST".equals(requestLine.getMethod())) {
                int contentLength = Integer.valueOf(requestHeaders.get("Content-Length"));
                requestBody = RequestBody.parse(IOUtils.readData(br, contentLength));
            }

            if ("POST".equals(requestLine.getMethod())
                    && "/user/create".equals(requestLine.getPath())) {
                logger.debug("Create new user");

                User newUser = Optional.ofNullable(requestBody)
                        .map(parameters -> newUser(parameters))
                        .orElseThrow(IllegalArgumentException::new);

                DataBase.addUser(newUser);

                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, INDEX_HTML);
                responseBody(dos, null);
                return;
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = getContentBy(requestLine.getPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private User newUser(RequestBody requestBody) {
        return new User(
                requestBody.getParameter("userId"),
                requestBody.getParameter("password"),
                StringUtils.unescape(requestBody.getParameter("name")),
                StringUtils.unescape(requestBody.getParameter("email"))
        );
    }

    private byte[] getContentBy(String path) throws IOException, URISyntaxException {
        Path templatePath = Paths.get(".", TEMPLATES_PATH, path);
        try {
            return FileIoUtils.loadFileFromClasspath(templatePath.toString());
        } catch (NullPointerException e) {
            throw new FileNotFoundException(path);
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

    private void response302Header(DataOutputStream dos, String location) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes(String.format("Location: %s \r\n", location));
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
