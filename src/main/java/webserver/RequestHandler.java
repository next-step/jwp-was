package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            RequestLine requestLine = RequestLine.parse(line);
            logger.debug("request line : {}", requestLine);

            String path = requestLine.getPath().getPath();
            logger.debug("request path : {}", path);

            Map<String, String> queryString = requestLine.getPath().getQueryString().getQueryData();
            logger.debug("request queryString : {}", queryString);

            String method = requestLine.getMethod();
            logger.debug("request method : {}", method);

            HttpHeader httpHeader = new HttpHeader();
            while (!"".equals(line)) {
                line = br.readLine();
                logger.debug("header : {}", line);
                httpHeader.addHeader(line);
            }

            RequestBody requestBody = new RequestBody();

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = "Hello World22".getBytes();
            if (path.endsWith(".html")) {
                body = FileIoUtils.loadFileFromClasspath("./templates"+path);
            } else if (path.equals("/user/create")) {
                requestBody.toBody(br, httpHeader);
                Map<String, String> parameters = requestBody.getParameters(requestBody.getBody());

                User user = createUser(parameters);
                logger.debug("createdUserId : {}", user.getUserId());
            }

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public User createUser(Map<String, String> queryString) {
        return new User(
                queryString.get("userId"),
                queryString.get("password"),
                queryString.get("name"),
                queryString.get("email")
        );
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
