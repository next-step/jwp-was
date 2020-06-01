package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static java.util.stream.Collectors.toMap;

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
            DataOutputStream dos = new DataOutputStream(out);

            String request = getRequestString(in);
            String [] requestLines = splitRequest(request);
            Map<String, String> queryStrings = parseQueryString(Arrays.asList(requestLines));

            byte[] body = "Hello World".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getRequestString(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return br.readLine();
    }

    String[] splitRequest(String request) {
        return request.split(" ");
    }

    Map<String, String> parseQueryString(List<String> requestLines) {
        if (Objects.isNull(requestLines) || requestLines.size() < 2 || !requestLines.get(1).contains("?")) {
            return Collections.emptyMap();
        }

        int queryStringBeginIndex = requestLines.get(1).indexOf("?");
        String queryString = requestLines.get(1).substring(queryStringBeginIndex + 1);
        return collectToParamMap(queryString);
    }

    private Map<String, String> collectToParamMap(String queryString) {
        return Arrays.stream(queryString.split("&"))
                .map(param -> param.split("="))
                .collect(toMap(entry -> entry[0], entry -> entry[1]));
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
