package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.HandlerMapper;
import webserver.handler.HandlerRegister;
import webserver.thread.ThreadPool;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final ThreadPoolExecutor THREAD_POOL = ThreadPool.execute();

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
            while ((connection = listenSocket.accept()) != null) {
                HandlerMapper command = new HandlerMapper(connection);
                HandlerRegister.register();

                try {
                    THREAD_POOL.submit(command);
                } catch (RejectedExecutionException e) {
                    logger.error("Client requests occurred more than thread pool size!!");
                    throw e;
                }
            }
        }
    }

}
