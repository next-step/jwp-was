package webserver.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webserver.http.request.HttpRequest;
import webserver.http.request.header.RequestHeader;
import webserver.http.request.method.HttpMethod;
import webserver.http.response.HttpResponse;

public class HttpServerHandler implements Runnable {
    private static final String NEXT_LINE = "\n";
    private static final Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);
    private static final String EMPTY = "";

    private final Socket connection;

    public HttpServerHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();

            StringBuilder requestContents = new StringBuilder(line);
            DataOutputStream dos = new DataOutputStream(out);

            while (isEnd(line)) {
                printlnLine(line);
                requestContents.append(NEXT_LINE);
                line = br.readLine();
                requestContents.append(line);
            }

            RequestHeader requestHeader = RequestHeader.create(requestContents.toString());

            HttpRequest httpRequest = new HttpRequest();
            byte[] body = httpRequest.run(requestHeader);

            HttpResponse httpResponse = new HttpResponse();
            String responseHeader = httpResponse.run(requestHeader, body.length);

            writeAndFlush(responseHeader, body, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void printlnLine(String line) {
        System.out.println(line);
    }

    private boolean isEnd(String line) {
        return !EMPTY.equals(line);
    }

    private static void writeAndFlush(String header, byte[] body, DataOutputStream dos) {
        try {
            dos.writeBytes(header);
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
