package webserver;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.StringUtils;
import webserver.http.RequestHeaders;
import webserver.http.RequestLine;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Paths;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();

            RequestLine requestLine = RequestLine.parse(line);
            RequestHeaders requestHeaders = new RequestHeaders();
            while (!"".equals(line = br.readLine())) {
                if (null == line) break;
                requestHeaders.add(line);
            }

            if ("GET".equals(requestLine.getMethod()) && requestLine.getPath().indexOf("/user/create") > -1) {
                    User newUser = new User(
                            requestLine.getParameter("userId"),
                            requestLine.getParameter("password"),
                            StringUtils.unescape(requestLine.getParameter("name")),
                            StringUtils.unescape(requestLine.getParameter("email"))
                    );

                    DataOutputStream dos = new DataOutputStream(out);
                    byte[] body = newUser.toString().getBytes();
                    response200Header(dos, body.length);
                    responseBody(dos, body);
            }

            DataOutputStream dos = new DataOutputStream(out);
            final String TEMPLATES_PATH = "templates";
            String template = Paths.get(".", TEMPLATES_PATH, requestLine.getPath()).toString();
            byte[] body = FileIoUtils.loadFileFromClasspath(template);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
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
