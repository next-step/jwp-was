package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int MIN_CORE_POOL_SIZE = 100;
    private static final int MAX_CORE_POOL_SIZE = 250;
    private static final long KEEP_ALIVE_TIME = 0L;
    private static final int QUEUE_SIZE = 100;

    public static void main(String args[]) throws Exception {
        int port = DEFAULT_PORT;
        int minCorPoolSize = MIN_CORE_POOL_SIZE;
        int maxCorPoolSize = MAX_CORE_POOL_SIZE;
        int queueSize = QUEUE_SIZE;

        if (args != null || args.length == 4) {
            port = Integer.parseInt(args[0]);
            minCorPoolSize = Integer.parseInt(args[1]);
            maxCorPoolSize = Integer.parseInt(args[1]);
            queueSize = Integer.parseInt(args[2]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            ThreadPoolExecutor executor = new ThreadPoolExecutor(minCorPoolSize,
                                                                 maxCorPoolSize,
                                                                 KEEP_ALIVE_TIME,
                                                                 TimeUnit.MILLISECONDS,
                                                                 new ArrayBlockingQueue<>(queueSize));
            while ((connection = listenSocket.accept()) != null) {
                executor.execute(new RequestHandler(connection));
            }
        }
    }
}
