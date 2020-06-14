package webserver;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebServerThreadPoolExecutor {
    public static final int DEFAULT_POOL_SIZE = 250;
    public static final int DEFAULT_QUEUE_SIZE = 100;

    private ExecutorService executorService;

    public static WebServerThreadPoolExecutor from(int poolSize, int queueSize) {
        return new WebServerThreadPoolExecutor(
            new ThreadPoolExecutor(
                poolSize,
                poolSize,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(queueSize)
            )
        );
    }

    public WebServerThreadPoolExecutor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void runServer(int port) throws IOException {
        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection));
            }
        }

        executorService.shutdown();
    }
}
