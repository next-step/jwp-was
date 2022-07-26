package webserver;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.RequestLine;
import webserver.http.request.parser.KeyValuePairParser;
import webserver.http.request.parser.ProtocolParser;
import webserver.http.request.parser.QueryParametersParser;
import webserver.http.request.parser.RequestLineParser;
import webserver.http.request.parser.URIParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())
        ) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            String requestLineMessage = bufferedReader.readLine();
            RequestLineParser requestLineParser = new RequestLineParser(new URIParser(new KeyValuePairParser(), new QueryParametersParser(new KeyValuePairParser())), new ProtocolParser());
            RequestLine requestLine = requestLineParser.parse(requestLineMessage);
            logger.info("[requestLine] = {}", requestLine);
            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
