package webserver;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebServer {

    private static final int DEFAULT_PORT = 8080;
    private static final int MAX_THREAD_POOL_SIZE = 250;
    private static final int THREAD_QUEUE_CAPACITY = 100;

    public static void main(String args[]) throws Exception {
        int port = DEFAULT_PORT;
        if (args != null && args.length != 0) {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(MAX_THREAD_POOL_SIZE, MAX_THREAD_POOL_SIZE
                    , 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(THREAD_QUEUE_CAPACITY));
            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                threadPoolExecutor.execute(new RequestHandler(connection));
            }
        }
    }
}
