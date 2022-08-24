package webserver;

import static controller.UserListController.CONTENT_TYPE;

import controller.LoginController;
import controller.UserCreateController;
import controller.UserListController;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import model.Cookie;
import model.HttpHeaders;
import model.request.HttpRequest;
import model.request.RequestBody;
import model.response.HttpResponse;
import model.response.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String CSS_PATH = "/css";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            RequestLine requestLine = (new RequestLine(br.readLine())).parse();
            HttpHeaders httpHeaders = createHttpHeaders(br);
            RequestBody requestBody = new RequestBody(br, httpHeaders);
            Cookie cookie = Cookie.from(httpHeaders.get(Cookie.REQUEST_COOKIE_HEADER));
            HttpRequest httpRequest = new HttpRequest(httpHeaders, requestLine, requestBody, cookie);
            HttpResponse httpResponse = new HttpResponse();
            DataOutputStream dos = new DataOutputStream(out);
            String path = httpRequest.getHttpPath();
            if (path.endsWith(".html")) {
                path = "./templates" + path;
                byte[] body = FileIoUtils.loadFileFromClasspath(path);
                httpResponse.forward(new ResponseBody(body), CONTENT_TYPE);
            } else if ("/user/create".equals(path)) {
                UserCreateController userCreateController = new UserCreateController();
                userCreateController.doPost(httpRequest, httpResponse);
            } else if ("/user/login".equals(path)) {
                LoginController loginController = new LoginController();
                loginController.doPost(httpRequest, httpResponse);
            } else if ("/user/list".equals(path)) {
                UserListController userListController = new UserListController();
                userListController.doGet(httpRequest, httpResponse);
            } else if (isStaticPath(path)) {
                String prefix = "static";
                if (path.contains(".html") || path.contains(".ico")) {
                    prefix = "templates";
                }

                byte[] body = FileIoUtils.loadFileFromClasspath("./" + prefix + "/" + path);
                httpResponse.forward(new ResponseBody(body), CONTENT_TYPE);
            }

            byte[] body = "Hello World".getBytes();
            httpResponse.forward(new ResponseBody(body), CONTENT_TYPE);
            httpResponse.writeResponse(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private HttpHeaders createHttpHeaders(BufferedReader br) throws IOException {
        List<String> headerLine = new ArrayList<>();
        String line = br.readLine();
        while (!line.equals("")) {
            headerLine.add(line);
            line = br.readLine();
        }

        return new HttpHeaders(String.join("\n", headerLine));
    }

    private boolean isStaticPath(String path) {
        return path.startsWith(CSS_PATH);
    }
}
