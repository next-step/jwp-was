package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.*;
import webserver.resolver.HandlebarsViewResolver;
import webserver.resolver.HtmlViewResolver;
import webserver.resolver.ViewResolver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            ViewResolver htmlViewResolver = new HtmlViewResolver();
            ViewResolver handlebarsViewResolver = new HandlebarsViewResolver();
            List<Handler> handlers = Arrays.asList(
                    new HomeRequestMappingHandler(htmlViewResolver),
                    new UserCreateRequestMappingHandler(htmlViewResolver),
                    new LoginRequestMappingHandler(htmlViewResolver),
                    new UserListRequestMappingHandler(handlebarsViewResolver),
                    new TemplateResourceHandler(htmlViewResolver)
            );

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, handlers));
                thread.start();
            }
        }
    }
}
