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
public class WebServerRunner {
    public static final int DEFAULT_POOL_SIZE = 5;
    public static final int DEFAULT_QUEUE_SIZE = 5;
    private static final long DEFAULT_TERMINATION_AWAIT_TIMEOUT = 1000;

    private ExecutorService executorService;

    public static WebServerRunner from(int poolSize, int queueSize) {
        return new WebServerRunner(
            new ThreadPoolExecutor(
                poolSize,
                poolSize,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(queueSize)
            )
        );
    }

    public WebServerRunner(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void execute(int port) throws IOException, InterruptedException {
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
        executorService.awaitTermination(DEFAULT_TERMINATION_AWAIT_TIMEOUT, TimeUnit.SECONDS);
    }
}
