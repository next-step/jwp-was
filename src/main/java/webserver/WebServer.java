package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.threadPool.ThreadPoolExecutorFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_CORE_POOL_SIZE = 4;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 5;
    private static final long DEFAULT_KEEP_ALIVE_TIME = 10L;
    private static final int DEFAULT_MAXIMUM_QUEUE_SIZE = 5;

    public static void main(String args[]) throws Exception {
        final int port = args != null && args.length > 0 && args[0] != null ? Integer.valueOf(args[0]) : DEFAULT_PORT;
        final int corePoolSize = args != null && args.length > 1 && args[1] != null ? Integer.valueOf(args[1]) : DEFAULT_CORE_POOL_SIZE;
        final int maximumPoolSize = args != null && args.length > 2 && args[2] != null  ? Integer.valueOf(args[2]) : DEFAULT_MAXIMUM_POOL_SIZE;
        final long keepAliveTime = args != null && args.length > 3 && args[3] != null  ? Long.valueOf(args[3]) : DEFAULT_KEEP_ALIVE_TIME;
        final int maximumQueueSize = args != null && args.length > 4 && args[4] != null  ? Integer.valueOf(args[4]) : DEFAULT_MAXIMUM_QUEUE_SIZE;

        Executor threadPool = ThreadPoolExecutorFactory.create(corePoolSize, maximumPoolSize, keepAliveTime, maximumQueueSize);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                threadPool.execute(new RequestHandler(connection));
            }
        }
    }
}
