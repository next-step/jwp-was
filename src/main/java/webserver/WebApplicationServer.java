package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int MAXIMUM_THREAD_POOL_SIZE = 250;
    private static final long KEEP_ALIVE_TIME = 0L;
    private static final int WORK_QUEUE_SIZE = 100;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        ExecutorService executorService = new ThreadPoolExecutor(
                MAXIMUM_THREAD_POOL_SIZE,
                MAXIMUM_THREAD_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(WORK_QUEUE_SIZE)
        );

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection));
            }
        } catch (Exception e) {
            logger.error("server error: " + e.getMessage());
        }
    }
}
