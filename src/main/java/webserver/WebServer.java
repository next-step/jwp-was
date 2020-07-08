package webserver;

import http.RequestHandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {

        RequestHandlerMapping.init();

        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 쓰레드 풀을 생성한다.
            BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(100);
            ThreadPoolExecutor executor = new ThreadPoolExecutor(
                    250, 250, 3000, TimeUnit.MILLISECONDS, workQueue);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                System.out.println("ActiveCount: " + executor.getActiveCount());
                executor.execute(new RequestHandler(connection));
            }
        }
    }
}
