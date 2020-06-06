package webserver;

import controller.RequestController;
import controller.UserController;
import http.RequestLine;
import http.RequestLineParser;
import http.RequestUrl;
import http.request.Request;
import http.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATE_PATH = "./templates";
    private static final String CONTENT_LENGTH = "Content-Length";
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
            byte[] bytes;

            Request request = readRequest(br);
            RequestController controller = new RequestController();
            initController(controller);

            RequestUrl requestUrl = request.findRequestUrl();
            if (requestUrl.isNone()) {
                logger.info("path : {}", request.getPath());
                bytes = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + request.getPath());
                DataOutputStream dos = new DataOutputStream(out);
                response(dos, Response.ofOk(bytes));
            }

            String methodName = requestUrl.getMethodName();
            Method method = RequestController.class.getMethod(methodName, Request.class, ViewHandler.class);
            Object viewHandlerObject = method.invoke(controller, request, new ViewHandler());
            DataOutputStream dos = new DataOutputStream(out);
            ViewHandler viewHandler = (ViewHandler) viewHandlerObject;


            if (viewHandler.isFile()) {
                bytes = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + viewHandler.getView());
                Response response = Response.ofOk(bytes);
                response.putCookie(viewHandler.getCookie() + "; Path=/");
                response(dos, response);
                return;
            }

            if (viewHandler.isTemplate()) {
                bytes = viewHandler.getView().getBytes();
                Response response = Response.ofOk(bytes);
                response(dos, response);
                return;
            }

            response(dos, Response.ofFound(viewHandler.getView()));

        } catch (IOException | URISyntaxException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void initController(RequestController controller) {
        controller.setUserController(new UserController());
    }

    private Request readRequest(BufferedReader br) throws IOException {
        Map<String, String> header = new HashMap<>();

        String line = br.readLine();
        RequestLine requestLine = RequestLineParser.parse(line);
        logger.info(line);
        while (isNotEmpty(line)) {
            line = br.readLine();
            putHeader(header, line);
            logger.info(line);
        }

        String body = IOUtils.readData(br, Integer.parseInt(header.getOrDefault(CONTENT_LENGTH, "0")));
        logger.info(body);
        return new Request(requestLine, header, body);
    }

    private void putHeader(Map<String, String> header, String line) {
        String[] values = line.split(":");
        if (values.length > 1) {
            header.put(values[0], values[1].trim());
        }
    }

    private boolean isNotEmpty(String line) {
        return line != null && !"".equals(line);
    }

    private void response(DataOutputStream dos, Response response) {
        try {
            dos.writeBytes(response.makeStatus());
            List<String> headers = response.makeHeader();
            for (String header : headers) {
                dos.writeBytes(header);
            }
            dos.writeBytes("\r\n");
            dos.write(response.getBody(), 0, response.getBodyLength());
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
