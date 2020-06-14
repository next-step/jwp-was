package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

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
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String requestLineText = parseRequestLine(br);
            RequestLine requestLine = RequestLine.of(requestLineText);

            List<String> requestHeaderTexts = parseRequestHeader(br);
            RequestHeaders requestHeaders = RequestHeaders.of(requestHeaderTexts);

            ResponseBody responseBody = ResponseBody.of(requestLine);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, responseBody.getContentType(), responseBody.getLength());
            responseBody(dos, responseBody.getFile());
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private String parseRequestLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private List<String> parseRequestHeader(BufferedReader br) throws IOException {
        List<String> requestHeaderTexts = new ArrayList<>();
        String line;
        do {
            line = br.readLine();
            if (StringUtils.isNull(line)) {
                break;
            }
            requestHeaderTexts.add(line);
        } while (!"".equals(line));
        return requestHeaderTexts;
    }

    private void response200Header(DataOutputStream dos, String contentType, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
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
