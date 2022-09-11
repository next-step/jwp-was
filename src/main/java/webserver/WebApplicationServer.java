package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplicationServer {

    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8888;
    private static final int THREAD_POOL_SIZE = 250;
    private static final long KEEP_ALIVE_TIME = 0L;
    private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final int WORK_QUEUE_SIZE = 100;

    public static void main(String args[]) throws Exception {
        // 스레드풀을 생성한다.
        ExecutorService es = new ThreadPoolExecutor(
                THREAD_POOL_SIZE,
                THREAD_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                new LinkedBlockingQueue<>(WORK_QUEUE_SIZE)
        );

        // 포트번호를 생성한다.
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // Router 를 생성한다.
        final Router router = new Router();

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                es.execute(new RequestHandler(connection, router));
            }
        }
    }
}
