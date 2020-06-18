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
    private static final int DEFAULT_KEEP_ALIVE_TIME = 10;
    private static final int DEFAULT_MAXIMUM_QUEUE_SIZE = 5;

    public static void main(String args[]) throws Exception {
        final int port = parseOrDefault(args, 0, DEFAULT_PORT);
        final int corePoolSize = parseOrDefault(args,1, DEFAULT_CORE_POOL_SIZE);
        final int maximumPoolSize = parseOrDefault(args,2, DEFAULT_MAXIMUM_POOL_SIZE);
        final long keepAliveTime = parseOrDefault(args,3, DEFAULT_KEEP_ALIVE_TIME);
        final int maximumQueueSize = parseOrDefault(args, 4, DEFAULT_MAXIMUM_QUEUE_SIZE);

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

    private static int parseOrDefault(String[] args, int i, int defaultValue) {
        if (args == null) {
            return defaultValue;
        }

        if (args.length <= i || args[i] == null) {
            return defaultValue;
        }

        return Integer.valueOf(args[i]);
    }

}
