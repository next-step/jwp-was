package webserver;

import domain.user.CreateUserController;
import domain.user.LoginController;
import domain.user.UserListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.HandlebarsCompiler;
import webserver.controller.ControllerMapper;
import webserver.controller.ControllerProvider;
import webserver.controller.ResourceController;
import webserver.http.session.InMemorySessionStore;
import webserver.http.session.SessionStore;
import webserver.http.session.UUIDBaseHttpSessionGenerator;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static webserver.controller.SecureController.secure;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    private static final Executor threadPool = Executors.newFixedThreadPool(100);

    public static void main(final String... args) throws Exception {
        final int port = getPort(args);
        final ControllerProvider controllerProviders = getHandlerProvider();
        final SessionStore sessionStore = getSessionStore();

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (final ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            while (true) {
                final Socket connection = listenSocket.accept();
                final Runnable requestHandler = new RequestHandler(connection, controllerProviders, sessionStore);

                threadPool.execute(requestHandler);
            }
        }
    }

    private static SessionStore getSessionStore() {
        return InMemorySessionStore.with(new UUIDBaseHttpSessionGenerator());
    }

    private static ControllerProvider getHandlerProvider() {
        final ControllerMapper handlerMapper = new ControllerMapper();

        handlerMapper.register("^\\/(css|fonts|images|js)\\/(.)*", new ResourceController("static"));
        handlerMapper.register("(.)*.html$", new ResourceController("templates"));
        handlerMapper.register(CreateUserController.PATH, new CreateUserController());
        handlerMapper.register(LoginController.PATH, new LoginController());
        handlerMapper.register(UserListController.PATH,
                secure(new UserListController(HandlebarsCompiler.of("/templates", ".html"))));

        return handlerMapper;
    }

    private static int getPort(final String... args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }

        return Integer.parseInt(args[0]);
    }
}
