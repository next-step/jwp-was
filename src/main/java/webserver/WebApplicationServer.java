package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(final String[] args) throws Exception {
        final int port = defaultPortIfEmpty(args);

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (var listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            connect(listenSocket);
        }
    }

    private static void connect(ServerSocket listenSocket) throws IOException {
        Socket connection;
        while ((connection = listenSocket.accept()) != null) {
            var thread = new Thread(new RequestHandler(connection));
            thread.start();
        }
    }

    private static int defaultPortIfEmpty(final String[] args) {
        if (args == null || args.length == 0) {
            return DEFAULT_PORT;
        }
        return Integer.parseInt(args[0]);
    }
}
