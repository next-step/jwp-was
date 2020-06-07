package http;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.ui.UserController;
import utils.IOUtils;
import webserver.RequestHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class RequestMappingHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final BufferedReader bufferedReader;
    private final DataOutputStream dataOutputStream;

    public RequestMappingHandler(final BufferedReader bufferedReader, final DataOutputStream dataOutputStream) throws IOException {
        this.bufferedReader = bufferedReader;
        this.dataOutputStream = dataOutputStream;
    }

    public void read() throws IOException {
        String readLine = bufferedReader.readLine();
        String firstLine = readLine;
        int contentLength = 0;
        logger.debug(readLine);

        while (!"".equals(readLine)) {
            readLine = bufferedReader.readLine();
            if (readLine.matches("Content-Length:.*")) {
                String[] lengths = readLine.split(":");
                contentLength = Integer.valueOf(lengths[1].trim());
            }
            logger.debug(readLine);
        }

        String requestBody = IOUtils.readData(bufferedReader, contentLength);
        if (Strings.isBlank(requestBody)) {
            handler(firstLine);
            return;
        }
        handler(firstLine, requestBody);

    }

    private void handler(String readLine, String requestBody) {
        RequestLine requestLine = RequestLineParser.parse(readLine, requestBody);
        String path = requestLine.getPath();
        if (path.matches("/user/.*")) {
            handlerByUserController(requestLine);
        }
    }

    private void handler(String readLine) {
        RequestLine requestLine = RequestLineParser.parse(readLine);
        String path = requestLine.getPath();
        if (path.matches("/user/.*")) {
            handlerByUserController(requestLine);
        }
    }

    private void handlerByUserController(RequestLine requestLine) {
        byte[] view = new UserController(requestLine).mapping();
        response200Header(view.length);
        responseBody(view);
    }

    private void response200Header(int lengthOfBodyContent) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
            dataOutputStream.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dataOutputStream.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dataOutputStream.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RequestMappingHandler that = (RequestMappingHandler) o;
        return Objects.equals(bufferedReader, that.bufferedReader) &&
                Objects.equals(dataOutputStream, that.dataOutputStream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bufferedReader, dataOutputStream);
    }
}
