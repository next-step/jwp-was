package webserver;

import controller.RequestController;
import controller.UserController;
import http.request.Request;
import http.request.RequestUrl;
import http.request.StyleSheet;
import http.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import view.ViewHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URISyntaxException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_PATH = "./static";
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
            byte[] bytes;

            Request request = new Request(br);
            RequestController controller = new RequestController();
            initController(controller);

            if (request.isStylesheet()) {
                bytes = FileIoUtils.loadFileFromClasspath(STATIC_PATH + parsing(request.getPath()));
                Response response = Response.ofStyleSheet(bytes);
                response.write(dos);
                return;
            }
            RequestUrl requestUrl = request.findRequestUrl();
            String methodName = requestUrl.getMethodName();
            Method method = RequestController.class.getMethod(methodName, Request.class, ViewHandler.class);
            Object viewHandlerObject = method.invoke(controller, request, new ViewHandler());
            ViewHandler viewHandler = (ViewHandler) viewHandlerObject;

            Response response = viewHandler.handle();
            response.write(dos);

        } catch (IOException | URISyntaxException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String parsing(String path) {
        // "/user" 가 prefix로 붙어서 제거하기 위한 parsing
        String[] files = path.split("\\.");
        StyleSheet styleSheet = StyleSheet.findByExtension(files[files.length - 1]);
        return path.substring(path.indexOf(styleSheet.getLocation()));
    }

    private void initController(RequestController controller) {
        controller.setUserController(new UserController());
    }

}
