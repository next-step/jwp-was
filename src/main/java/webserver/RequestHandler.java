package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.CreateMemberHandler;
import webserver.handler.ListMemberHandler;
import webserver.handler.LoginMemberHandler;
import webserver.handler.StaticFileHandler;
import webserver.http.*;
import webserver.view.View;
import webserver.view.ViewResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final HttpSessionStore SESSION_STORE = new ApplicationHttpSessionStore(new RandomUuidGenerator());

    private final Socket connection;

    private final HandlerMapping handlerMapping = new HandlerMapping(List.of(
            new RequestMappingRegistration("/user/create", HttpMethod.POST, new CreateMemberHandler()),
            new RequestMappingRegistration("/user/list", HttpMethod.GET, new ListMemberHandler()),
            new RequestMappingRegistration("/user/login", HttpMethod.POST, new LoginMemberHandler())),
            new StaticFileHandler());

    private final ViewResolver viewResolver = new ViewResolver("/templates", ".html");

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.create(in);

            HttpResponse httpResponse = new HttpResponse(out);

            handleRequest(httpRequest, httpResponse);

            httpResponse.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        initSession(httpRequest, httpResponse);

        Handler handler = handlerMapping.getHandler(httpRequest);

        ModelAndView modelAndView = handler.handle(httpRequest, httpResponse);

        if (modelAndView == null) {
            return;
        }

        View view = viewResolver.resolveView(modelAndView.getView());

        view.render(modelAndView.getModel(), httpResponse);
    }

    private void initSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = getHttpSession(httpRequest);

        if (httpSession == null) {
            httpSession = createHttpSession(httpRequest);
        }

        addSessionCookie(httpResponse, httpSession);
    }

    private HttpSession createHttpSession(HttpRequest httpRequest) {
        HttpSession httpSession = SESSION_STORE.createHttpSession();

        httpRequest.initHttpSession(httpSession);

        return httpSession;
    }

    private HttpSession getHttpSession(HttpRequest httpRequest) {
        String sessionIdFromCookie = httpRequest.getCookie("JWP_SESSION");

        return SESSION_STORE.getSession(sessionIdFromCookie);
    }

    private static void addSessionCookie(HttpResponse httpResponse, HttpSession httpSession) {
        Cookie sessionCookie = new Cookie("JWP_SESSION", httpSession.getId(), "/");

        httpResponse.addCookie(sessionCookie);
    }
}
