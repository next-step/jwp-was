package webserver;

import java.io.*;
import java.net.Socket;
import java.util.*;

import controller.CommonController;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.AnnotationHandlerMapping;
import web.method.InvocableHandlerMethod;
import web.sevlet.View;

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
            HttpResponse httpResponse = HttpResponse.from(dos);

            InvocableHandlerMethod handlerMethod = annotationHandlerMapping.getHandler(httpRequest);

            if(handlerMethod != null) {

                Object object = handlerMethod.invoke(httpRequest, httpResponse);

                if(object instanceof View) {
                    render((View) object, httpRequest, httpResponse);
                }
                return;
            }

            if(!httpResponse.isResponseDone()) {
                response404(dos);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response404(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 404 Not Found\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void render(View view, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        view.render(new HashMap<>(), httpRequest, httpResponse);
        httpResponse.responseDone();
    }
}
