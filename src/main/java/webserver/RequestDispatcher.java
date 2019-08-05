package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.handler.RequestHandler;
import webserver.http.request.handler.RequestHandlerType;
import webserver.http.request.handler.DefaultRequestHandler;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ViewRenderer;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;

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

            HttpRequest httpRequest = new HttpRequest(new BufferedReader(new InputStreamReader(in)));
            HttpResponse httpResponse = new HttpResponse(new DataOutputStream(out));

            // get handler
            RequestHandler handler = getHandler(httpRequest);

            // view: static resource, template engine, json
            ViewRenderer viewRenderer = handler.handle(httpRequest, httpResponse);
            viewRenderer.render();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestHandler getHandler(HttpRequest httpRequest) {

        String requestPath = httpRequest.getPath();
        for (RequestHandlerType handlerType: RequestHandlerType.values()) {
            Matcher matcher = handlerType.getPattern().matcher(requestPath);
            if (matcher.find()) {
                return handlerType.getHandler();
            }
        }
        return new DefaultRequestHandler();
    }
}
