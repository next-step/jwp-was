package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpMethod;
import webserver.http.request.Request;
import webserver.http.response.Response;

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
            Response response = new Response(out, path);

            if (StringUtils.equals(request.getRequestPath(), USER_CREATE_PATH) && request.getHttpMethod() == HttpMethod.POST) {
                createUser(response, request.getRequestBody());
                return;
            }

            byte[] body = response.getBody(path);

            response200Header(response, body.length);
            responseBody(response, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    private void createUser(Response response, Map<String, String> requestBody) {
        User user = new User(requestBody.get("userId"), requestBody.get("password"), requestBody.get("name"), requestBody.get("email"));
        DataBase.addUser(user);

        response302Header(response, ROOT_FILE);
    }

    private String getPathFromRequest(Request request) {
        return request.getRequestPath();
    }

    private void response200Header(Response response, int lengthOfBodyContent) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes(StringUtils.join(response.getHeaders(), ": "));
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(Response response, String redirectPath) {
        DataOutputStream dos = response.getResponse();
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: " + redirectPath + "\r\n");
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
