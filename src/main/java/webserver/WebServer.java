package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.handler.CreateUserHandlerProvider;
import webserver.http.handler.LoginHandlerProvider;
import webserver.http.handler.PatternMatchHandlerProvider;
import webserver.http.handler.ResourceHandler;
import webserver.http.handler.HandlerProvider;

public class WebServer {

    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(final String... args) throws Exception {
        final int port = getPort(args);
        final List<HandlerProvider> handlerProviders = getHandlerProviders();

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (final ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            while (true) {
                final Socket connection = listenSocket.accept();
                final Runnable requestHandler = new RequestHandler(connection, handlerProviders);
                final Thread thread = new Thread(requestHandler);

                thread.start();
            }
        }
    }

    private static List<HandlerProvider> getHandlerProviders() {
        final HandlerProvider templatesResourceProvider = new PatternMatchHandlerProvider("(.)*.html$",
                new ResourceHandler("templates"));
        final HandlerProvider staticResourceProvider = new PatternMatchHandlerProvider(
                "(.)*.(css|fonts|images|js)$", new ResourceHandler("static"));

        return List.of(
                templatesResourceProvider,
                staticResourceProvider,
                new CreateUserHandlerProvider(),
                new LoginHandlerProvider()
        );
    }

    private static int getPort(final String... args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }

        return Integer.parseInt(args[0]);
    }
}
