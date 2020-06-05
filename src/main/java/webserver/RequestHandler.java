package webserver;

import controller.RequestController;
import http.QueryString;
import http.RequestLine;
import http.RequestLineParser;
import http.RequestUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;

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
    private static final String TEMPLATE_PATH = "./templates";
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

            String line = br.readLine();
            logger.info(line);
            RequestLine requestLine = RequestLineParser.parse(line);

            while (isNotEmpty(line)) {
                line = br.readLine();
                logger.info(line);
            }

            RequestUrl requestUrl = RequestUrl.findByPath(requestLine.getPath());
            String methodName = requestUrl.getMethodName();

            if (requestLine.isExistQuery() && StringUtils.hasText(methodName)) {
                Object object = RequestController.class.newInstance();
                Method method = RequestController.class.getMethod(methodName, QueryString.class);
                method.invoke(object, requestLine.getPath().getQueryString());
            }

            logger.info("path : {}", requestLine.getPath().getPath());
            byte[] bytes = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + requestLine.getPath().getPath());

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, bytes.length);
            responseBody(dos, bytes);
        } catch (IOException | URISyntaxException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private boolean isNotEmpty(String line) {
        return line != null && !"".equals(line);
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
