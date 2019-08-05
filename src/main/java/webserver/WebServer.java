package webserver;

import domain.user.CreateUserHandler;
import domain.user.LoginHandler;
import domain.user.UserListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.HandlebarsCompiler;
import webserver.handler.HandlerMapper;
import webserver.handler.HandlerProvider;
import webserver.handler.ResourceHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static webserver.http.RequestMethod.GET;
import static webserver.http.RequestMethod.POST;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    private static final Executor threadPool = Executors.newFixedThreadPool(100);

    public static void main(final String... args) throws Exception {
        final int port = getPort(args);
        final HandlerProvider handlerProviders = getHandlerProvider();

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (final ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            while (true) {
                final Socket connection = listenSocket.accept();
                final Runnable requestHandler = new RequestHandler(connection, handlerProviders);

                threadPool.execute(requestHandler);
            }
        }
    }

    private static HandlerProvider getHandlerProvider() {
        final HandlerMapper handlerMapper = new HandlerMapper();

        handlerMapper.register("^\\/(css|fonts|images|js)\\/(.)*", GET, new ResourceHandler("static"));
        handlerMapper.register("(.)*.html$", GET, new ResourceHandler("templates"));
        handlerMapper.register("/user/create", POST, new CreateUserHandler());
        handlerMapper.register("/user/login", POST, new LoginHandler());
        handlerMapper.register("/user/list", GET,
                new UserListHandler(HandlebarsCompiler.of("/templates", ".html")));

        return handlerMapper;
    }

    private static int getPort(final String... args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }

        return Integer.parseInt(args[0]);
    }
}
