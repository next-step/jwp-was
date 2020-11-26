package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int THREAD_CORE_POOL_SIZE = 250;
    private static final int THREAD_MAXIMUM_POOL_SIZE = 250;
    private static final int KEEP_ALIVE_TIME = 3000;
    private static final int QUEUE_SIZE = 100;


    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(QUEUE_SIZE);
            ExecutorService threadPool = new ThreadPoolExecutor(THREAD_CORE_POOL_SIZE,
                    THREAD_MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, queue);

            Socket connection;

            while ((connection = listenSocket.accept()) != null) {
                threadPool.execute(new RequestHandler(connection));
            }

        }
    }
}
