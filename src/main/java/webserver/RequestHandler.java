package webserver;

import http.requestline.RequestLine;
import http.requestline.RequestLineParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RequestHandler implements Runnable {
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            String line = bufferedReader.readLine();
            RequestLine requestLine = RequestLineParser.parse(line);
            while (!StringUtils.isEmpty(line)) {
                log.debug(line);
                line = bufferedReader.readLine();
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + requestLine.getPath());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
