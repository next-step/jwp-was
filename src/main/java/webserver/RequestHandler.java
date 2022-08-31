package webserver;

import static controller.UserListController.CONTENT_TYPE;

import controller.Controller;
import controller.RequestMapper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import model.Cookie;
import model.HttpHeaders;
import model.request.HttpRequest;
import model.request.RequestBody;
import model.response.HttpResponse;
import model.response.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

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
            HttpHeaders httpHeaders = new HttpHeaders(String.join("\n", IOUtils.readHeaderData(br)));
            RequestBody requestBody = new RequestBody(br, httpHeaders);
            Cookie cookie = Cookie.from(httpHeaders.get(Cookie.REQUEST_COOKIE_HEADER));
            HttpRequest httpRequest = new HttpRequest(httpHeaders, requestLine, requestBody, cookie);
            HttpResponse httpResponse = HttpResponse.init();
            DataOutputStream dos = new DataOutputStream(out);
            String path = httpRequest.getHttpPath();
            RequestMapper requestMapper = new RequestMapper();
            Controller controller = requestMapper.getController(path);
            controller.service(httpRequest, httpResponse);

            byte[] body = "Hello World".getBytes();
            HttpResponse.forward(new ResponseBody(body), CONTENT_TYPE);
            httpResponse.writeResponse(dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean isStaticPath(String path) {
        return path.startsWith(CSS_PATH);
    }
}
