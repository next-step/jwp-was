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
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            ThreadPoolExecutor executor = new ThreadPoolExecutor(MIN_CORE_POOL_SIZE,
                                                                 MAX_CORE_POOL_SIZE,
                                                                 KEEP_ALIVE_TIME,
                                                                 TimeUnit.MILLISECONDS,
                                                                 new ArrayBlockingQueue<>(QUEUE_SIZE));
            while ((connection = listenSocket.accept()) != null) {
                executor.execute(new RequestHandler(connection));
            }
        }
    }
}
