package webserver;

import java.io.*;
import java.net.Socket;
import java.net.http.HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.http.HttpHeaders;
import webserver.http.RequestLine;

import static java.lang.System.lineSeparator;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    RequestHandler(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! [ConnectedIP={}, Port={}]", connection.getInetAddress(),
                connection.getPort());

        try (connection;
             final InputStream in = connection.getInputStream();
             final OutputStream out = connection.getOutputStream()) {
            final BufferedReader requestReader = new BufferedReader(new InputStreamReader(in));

            final RequestLine requestLine = RequestLine.parse(requestReader.readLine());
            final HttpHeaders httpHeaders = readHeaders(requestReader);

            logger.debug("Parse header [RequestLine={}, HttpHeaders={}]", requestLine, httpHeaders);

            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            final DataOutputStream dos = new DataOutputStream(out);
            final byte[] body = "Hello World".getBytes();

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpHeaders readHeaders(final BufferedReader requestReader) throws IOException {
        final StringBuilder rawHeadersBuilder = new StringBuilder();

        String readRawHeader = requestReader.readLine();
        while (StringUtils.isNotBlank(readRawHeader)) {
            rawHeadersBuilder.append(readRawHeader).append(lineSeparator());

            readRawHeader = requestReader.readLine();
        };

        return HttpHeaders.of(rawHeadersBuilder.toString());
    }

    private void response200Header(final DataOutputStream dos,
                                   final int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(final DataOutputStream dos,
                              final byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
