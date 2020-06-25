package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


import handler.DispatcherHandler;
import handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_THREAD_POOL_SIZE = 250;
    private static final int DEFAULT_QUEUE_SIZE = 100;

    public static void main(String args[]) throws Exception {
        int port = 0;
        int threadPoolSize = 0;
        int queueSize = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
            threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
            queueSize = DEFAULT_QUEUE_SIZE;
        } else {
            port = Integer.parseInt(args[0]);
            threadPoolSize = Integer.parseInt(args[1]);
            queueSize = Integer.parseInt(args[2]);
        }

        ExecutorService threadPool = ThreadPoolFactory.newFixedThreadPoolWithQueueSize(threadPoolSize, queueSize);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Handler dispatcherHandler = DispatcherHandler.getInstance();

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                threadPool.execute(new RequestHandler(connection, dispatcherHandler));
            }
        }
    }
}
