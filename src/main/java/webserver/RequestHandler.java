package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
import webserver.request.domain.model.User;
import webserver.request.domain.request.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private User user;

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
//    private RequestLine requestLine;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            if (in != null) {
                httpRequest = new HttpRequest(in);

                DataOutputStream dos = new DataOutputStream(out);

                httpResponse = new HttpResponse(dos);
                matchResponse(httpRequest);
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void matchResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();
        String method = httpRequest.getMethod();

        if (path.equals("/user/create")) {
            if (method.equals("GET")) {
                QueryString queryString = httpRequest.getQueryString();
                httpResponse.redirect(parseQueryString(path, queryString));
            } else {
                RequestBody requestBody = httpRequest.getBody();
                httpResponse.redirect(parseBody(path, requestBody));
            }
        } else {
            if (path.endsWith("html")) {
                httpResponse.forward(IOUtils.loadFileFromClasspath(path));
            }
        }
    }

    private String parseQueryString(String parsePath, QueryString queryString) {
        if (queryString != null) {
            String[] str = parsePath.split("/");
            if (str.length > 2) {
                if (str[1].equals("user")) {
                    userController(queryString.getDataPairs(), str);
                }
            }
        }

        return "/index.html";
    }

    private String parseBody(String parsePath, RequestBody requestBody) {
        String[] str = parsePath.split("/");
        if (str.length > 2) {
            if (str[1].equals("user")) {
                userController(requestBody.getDataPairs(), str);
            }
        }

        return "/index.html";
    }

    private void userController(Map<String, String> map, String[] str) {
        if (str[2].equals("create")) {
            this.user = User.create(map);
        }
    }
}
