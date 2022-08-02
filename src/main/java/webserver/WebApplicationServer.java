package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);

    private static final int DEFAULT_PORT = 8080;
    private static final int PORT_ARGS_INDEX = 0;

    private static final int DEFAULT_THREAD_POOL_SIZE = 250;
    private static final int THREAD_POOL_SIZE_ARGS_INDEX = 1;

    private static final int DEFAULT_THREAD_QUEUE_SIZE = 100;
    private static final int THREAD_QUEUE_SIZE_ARGS_INDEX = 2;

    /**
     * @param args {port} {thread pool size} {thread queue size}
     * @throws Exception socket exception
     */
    public static void main(String[] args) throws Exception {
        int port = port(args);
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutor(poolSize(args), queueCapacity(args));
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port(args))) {
            logger.info("Web Application Server started {} port.", port);
            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                threadPoolExecutor.execute(new RequestHandler(connection));
            }
        }
    }

    private static ThreadPoolExecutor threadPoolExecutor(int poolSize, int queueCapacity) {
        return new ThreadPoolExecutor(poolSize, poolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueCapacity));
    }

    private static int port(String[] args) {
        return extractIntByIndexOrDefault(args, PORT_ARGS_INDEX, DEFAULT_PORT);
    }

    private static int poolSize(String[] args) {
        return extractIntByIndexOrDefault(args, THREAD_POOL_SIZE_ARGS_INDEX, DEFAULT_THREAD_POOL_SIZE);
    }

    private static int queueCapacity(String[] args) {
        return extractIntByIndexOrDefault(args, THREAD_QUEUE_SIZE_ARGS_INDEX, DEFAULT_THREAD_QUEUE_SIZE);
    }

    private static int extractIntByIndexOrDefault(String[] args, int index, int defaultValue) {
        if (args == null || args.length < index + 1) {
            return defaultValue;
        }
        return Integer.parseInt(args[index]);
    }
}
