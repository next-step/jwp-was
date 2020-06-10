package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    private static final int INIT_SIZE_OF_THREAD_POOL = 10;
    private static final int SIZE_OF_THREAD_POOL = 250;
    private static final int KEEP_ALIVE_TIME = 30;
    private static final int WORK_QUEUE_CAPACITY = 100;
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            INIT_SIZE_OF_THREAD_POOL,
            SIZE_OF_THREAD_POOL, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(WORK_QUEUE_CAPACITY)
    );

    public static void main(String[] args) throws Exception {
        final int port = (args == null || args.length == 0) ? DEFAULT_PORT : Integer.parseInt(args[0]);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                THREAD_POOL_EXECUTOR.execute(new RequestHandler(connection));
            }
        }
    }
}
