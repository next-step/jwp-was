package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import http.controller.RequestMapper;
import http.controller.SignInController;
import http.controller.SignUpController;
import http.controller.UserListController;
import http.types.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        final int port = (args == null || args.length == 0) ? DEFAULT_PORT : Integer.parseInt(args[0]);
        
        // 초기화 부분 일단 하드코딩
        RequestMapper.addController("/user/create", HttpMethod.POST, new SignUpController());
        RequestMapper.addController("/user/login", HttpMethod.POST, new SignInController());
        RequestMapper.addController("/user/list", HttpMethod.GET, new UserListController());

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection));
                thread.start();
            }
        }
    }
}
