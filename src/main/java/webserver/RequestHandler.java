package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mvc.controller.Controller;
import mvc.controller.ControllerMatcher;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.session.Interceptor;
import webserver.session.InterceptorRegistry;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String ROOT = "/";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            HttpRequest request = HttpRequest.from(in);
            HttpResponse response = new HttpResponse();
            String path = getDefaultPath(request.getPath());
            Controller controller = ControllerMatcher.matchController(path);
            logger.debug("Controller : {}", controller);
            if (controller == null) {
                response.notFound();
                response.writeResponse(dos);
                return;
            }
            executeInterceptorsPreHandle(request, response);
            controller.service(request, response);
            response.writeResponse(dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void executeInterceptorsPreHandle(HttpRequest request, HttpResponse response) {
        InterceptorRegistry registry = new InterceptorRegistry();
        List<Interceptor> interceptors = registry.getInterceptors();
        for (Interceptor interceptor : interceptors) {
            if (!interceptor.preHandle(request, response)) {
                throw new IllegalArgumentException("인터셉터 작업이 중단되었습니다.");
            }
        }
    }

    private String getDefaultPath(String path) {
        if (ROOT.equals(path)) {
            return "/index.html";
        }
        return path;
    }
}
