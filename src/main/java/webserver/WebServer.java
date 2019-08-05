package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final int DEFAULT_THREAD_POOL_SIZE = 10;

    public static void main(String args[]) throws Exception {
        int port = getPort(args);

        ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            RequestDispatcher dispatcher = new RequestDispatcher();
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection, dispatcher));
            }

            executorService.shutdown();
            executorService.awaitTermination(100, TimeUnit.SECONDS);
            logger.info("Wep Application Server stopped {} port", port);
        }
    }
    private static int getPort(String[] args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }

        return Integer.parseInt(args[0]);
    }
}
