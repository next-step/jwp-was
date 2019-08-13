package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.common.session.SessionManager;
import webserver.http.request.HttpRequest;
import webserver.http.request.handler.RequestHandler;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;
import webserver.http.response.view.ViewRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestDispatcher implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
    private Socket connection;

    public RequestDispatcher(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            SessionManager.setSession(httpRequest);

            RequestHandler handler = WebConfig.getHandler(httpRequest.getPath());

            ModelAndView modelAndView = handler.handle(httpRequest, httpResponse);
            ViewRenderer viewRenderer = WebConfig.getViewRenderer(modelAndView.getViewName());
            viewRenderer.render(modelAndView, httpResponse);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
