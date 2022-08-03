package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.config.WebConfig;
import webserver.handlers.RequestHandler;
import webserver.handlers.ResponseHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;
    public static final int CORE_POOL_SIZE = 10;
    public static final int MAXIMUM_POOL_SIZE = 10;
    public static final int KEEP_ALIVE_TIME = 3;
    public static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    public static final int CAPACITY = 100;

    public static void main(String[] args) throws Exception {
        int port;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }
        ExecutorService executorService = new ThreadPoolExecutor(CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new LinkedBlockingQueue<>(CAPACITY));

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);
            WebConfig webConfig = new WebConfig();
            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(new FrontController(connection,
                        webConfig.getBean(RequestHandler.class),
                        webConfig.getBean(ResponseHandler.class)), executorService);

                future.get();
            }

            executorService.shutdown();
            executorService.awaitTermination(100, TimeUnit.SECONDS);
        }
    }
}
