package webserver;

import java.io.*;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import controller.CommonController;
import http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.AnnotationHandlerMapping;
import web.method.InvocableHandlerMethod;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private AnnotationHandlerMapping annotationHandlerMapping;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;

        Map<Class<?>, Object> controllers = new LinkedHashMap<>();
        controllers.put(CommonController.class, new CommonController());
        this.annotationHandlerMapping = new AnnotationHandlerMapping(controllers);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            HttpRequest httpRequest = HttpRequest.from(bufferedReader);

            InvocableHandlerMethod handlerMethod = annotationHandlerMapping.getHandler(httpRequest);

            if(handlerMethod != null) {
                byte[] bytes = (byte[]) handlerMethod.invoke(httpRequest);

                dos.writeBytes("HTTP/1.1 200 OK \r\n");
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                dos.writeBytes("Content-Length: " + bytes.length + "\r\n");
                dos.writeBytes(System.lineSeparator());
                dos.write(bytes, 0, bytes.length);
                dos.flush();
                return;
            }


            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
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
