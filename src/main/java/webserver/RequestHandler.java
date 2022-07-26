package webserver;

import model.HttpMessage;
import model.HttpMessageData;
import model.RequestLine;
import model.UrlPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            boolean end = false;
            List<String> data = new ArrayList<>();
            while (!end) {
                String line = bufferedReader.readLine();
                end = (!StringUtils.hasText(line));
                data.add(line);
            }

            HttpMessageData httpMessageData = new HttpMessageData(data);
            logger.info("Request data >>>>>>" + httpMessageData.toStringHttpMessageData());

            HttpMessage httpMessage = new HttpMessage(new HttpMessageData(httpMessageData.getHttpMessageData()));
            RequestLine requestLine = httpMessage.getRequestLine();

            // find handler and invoke

            byte[] body = null;
            if (requestLine.getUrlPath().hasExtension()) {
                UrlPath urlPath = requestLine.getUrlPath();
                body = FileIoUtils.loadFileFromClasspath(urlPath.getPath());
            }

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, byte[] body) {

        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            if (body == null) {
                dos.writeBytes("\r\n");
                return;
            }

            dos.writeBytes("Content-Length: " + body.length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            if (body == null) {
                dos.flush();
                return;
            }
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
