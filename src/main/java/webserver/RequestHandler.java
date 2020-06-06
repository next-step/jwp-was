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
import utils.IOUtils;

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
            Map<String, String> header = new HashMap<>();

            String line = br.readLine();
            RequestLine requestLine = RequestLineParser.parse(line);
            logger.info(line);
            while (isNotEmpty(line)) {
                line = br.readLine();
                logger.info(line);
                putHeader(header, line);
            }

            if (requestLine.isPost()) {
                String body = IOUtils.readData(br, Integer.parseInt(header.getOrDefault(CONTENT_LENGTH, "0")));
                requestLine.addQueryString(body);
                logger.info(body);
            }

            RequestUrl requestUrl = RequestUrl.findByPath(requestLine.getPath());
            String methodName = requestUrl.getMethodName();
            boolean isLoginSuccess = false;
            byte[] bytes;
            if (requestLine.isExistQuery() && StringUtils.hasText(methodName)) {
                Object instance = RequestController.class.newInstance();
                Method method = RequestController.class.getMethod(methodName, QueryString.class);
                Object invoke = method.invoke(instance, requestLine.getQueryString());
                logger.info("invoke : {}", invoke == null ? "" : invoke.toString());
                DataOutputStream dos = new DataOutputStream(out);
                if (requestUrl.isUserLogin() && invoke != null) {
                    isLoginSuccess = (boolean) invoke;
                    bytes = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + (isLoginSuccess ? "/index.html" : "/user/login_failed.html"));
                    responseLoginHeader(dos, bytes.length, isLoginSuccess);
                    responseBody(dos, bytes);
                }
                response302Header(dos);
                return;
            }

            logger.info("path : {}", requestLine.getPath());

            bytes = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + requestLine.getPath());

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, bytes.length);
            responseBody(dos, bytes);
        } catch (IOException | URISyntaxException | NoSuchMethodException e) {
            logger.error(e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
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

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseLoginHeader(DataOutputStream dos, int lengthOfBodyContent, boolean isLoginSuccess) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            if (isLoginSuccess) {
                dos.writeBytes("Set-Cookie: logined=true; Path=/ \r\n");
            } else {
                dos.writeBytes("Set-Cookie: logined=false; Path=/ \r\n");
            }
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
