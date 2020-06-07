package webserver;

import controller.RequestController;
import controller.UserController;
import http.request.HttpRequest;
import http.request.RequestUrl;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, UTF_8));
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest request = new HttpRequest(br);
            HttpResponse response = new HttpResponse(dos);

            RequestController controller = initController();

            RequestUrl requestUrl = request.findRequestUrl();
            String methodName = requestUrl.getMethodName();
            Method method = RequestController.class.getMethod(methodName, HttpRequest.class, HttpResponse.class);
            method.invoke(controller, request, response);

        } catch (IOException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private RequestController initController() {
        RequestController requestController = new RequestController();
        requestController.setUserController(new UserController());
        return requestController;
    }

}
