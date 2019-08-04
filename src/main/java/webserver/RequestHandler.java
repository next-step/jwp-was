package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.HttpStringUtils;
import webserver.http.RequestLine;
import webserver.service.WebService;
import webserver.service.WebServiceFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            if (in == null) {
                return;
            }

            RequestLine requestLine = readRequestLine(in);
            WebService webService = WebServiceFactory.create(requestLine.getPath().getPath());
            if (webService != null) {
                webService.process(requestLine);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath(requestLine.getFilePath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException| URISyntaxException e) {
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

    public RequestLine readRequestLine(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;

        while (!"".equals(line = br.readLine())) {
            if (line == null) {
                break;
            }
            sb.append(line);
            sb.append("\n");
            logger.info("RequestHeader : {}", line);
        };

        return RequestLine.parse(HttpStringUtils.splitAndFindByIndex(sb.toString(), "\n", 0));
    }
}
