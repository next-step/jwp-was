package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.view.CookiesParser;
import webserver.http.view.request.RequestReader;
import webserver.http.view.HeadersParser;
import webserver.http.view.KeyValuePairParser;
import webserver.http.view.request.ParametersParser;
import webserver.http.view.ProtocolParser;
import webserver.http.view.request.RequestLineParser;
import webserver.http.view.request.URIParser;

import java.net.ServerSocket;
import java.net.Socket;

public class WebApplicationServer {
    private static final Logger logger = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final int DEFAULT_PORT = 8080;

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
            RequestReader requestReader = createRequestReader();
            while ((connection = listenSocket.accept()) != null) {
                Thread thread = new Thread(new RequestHandler(connection, requestReader));
                thread.start();
            }
        }
    }

    private static RequestReader createRequestReader() {
        KeyValuePairParser keyValuePairParser = new KeyValuePairParser();
        ParametersParser parametersParser = new ParametersParser(keyValuePairParser);
        URIParser uriParser = new URIParser(keyValuePairParser, parametersParser);
        CookiesParser cookiesParser = new CookiesParser(keyValuePairParser);

        return new RequestReader(
                new RequestLineParser(uriParser, new ProtocolParser()),
                new HeadersParser(keyValuePairParser),
                parametersParser,
                cookiesParser
        );
    }
}
