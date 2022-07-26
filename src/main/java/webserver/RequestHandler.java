package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpMethod;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import static model.Constant.ROOT_FILE;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final String USER_CREATE_PATH = "/user/create";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            Request request = new Request(in);
            String path = getPathFromRequest(request);
            logger.debug("requestPath : {}", path);

            if (StringUtils.equals(request.getRequestPath(), USER_CREATE_PATH) && request.getHttpMethod() == HttpMethod.POST) {
                Map<String, String> requestBody = request.getRequestBody();
                User user = new User(requestBody.get("userId"), requestBody.get("password"), requestBody.get("name"), requestBody.get("email"));
                logger.debug("user : {}", user);

                DataBase.addUser(user);
                path = ROOT_FILE;
            }

            Response response = new Response(out, path);
            byte[] body = response.getBody(path);

            response200Header(response, body.length);
            responseBody(response, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private String getPathFromRequest(Request request) {
        return request.getRequestPath();
    }

    private void response200Header(Response response, int lengthOfBodyContent) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(StringUtils.join(response.getHeader(), ": "));
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(Response response, byte[] body) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
