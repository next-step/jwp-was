package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

import common.CommonController;
import http.HttpSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.UserController;
import user.UserService;
import web.*;
import web.DefaultHttpRequestHandler;
import web.ResourceHttpRequestHandler;
import web.servlet.HandleBarsViewResolver;
import web.servlet.ViewResolverComposite;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        int corePoolSize = getArgument(args, 1, 100);
        int maximumPoolSize = getArgument(args, 2, 250);
        int queueSize = getArgument(args, 3, 100);

        Map<Class<?>, Object> controllers = new LinkedHashMap<>();
        controllers.put(CommonController.class, new CommonController());
        controllers.put(UserController.class, new UserController(new UserService()));
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping(controllers);

        ViewResolverComposite viewResolverComposite = new ViewResolverComposite(
                new LinkedHashSet<>(Arrays.asList(
                        new HandleBarsViewResolver())
                ));

        HttpRequestHandlerComposite httpRequestHandlerComposite = new HttpRequestHandlerComposite(
                new LinkedHashSet<>(Arrays.asList(
                        new DefaultHttpRequestHandler(annotationHandlerMapping, viewResolverComposite),
                        new ResourceHttpRequestHandler())
                ));

        HttpSessionManager sessionManager = new HttpSessionManager();

        Executor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(queueSize));

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executor.execute(new RequestHandler(connection, httpRequestHandlerComposite, sessionManager));
            }
        }
    }

    private static int getArgument(String[] args, int index, int defaultValue) {
        if(args.length > index) {
            try {
                return Integer.parseInt(args[index]);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }
}
