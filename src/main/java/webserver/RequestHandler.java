package webserver;

import static webserver.response.HttpStatusResponse.responseBodRequest400;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            HttpRequest httpRequest = parseRequest(inputStream);
            HttpResponse httpResponse = new HttpResponse();

            DispatcherServlet.INSTANCE.serve(httpRequest, httpResponse);

            writeResponse(dos, httpResponse);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (RuntimeException e) {  // 예상되는 예외의 최상위 클래스로 할 것 정의해야함
            logger.error(e.getMessage());
            try (OutputStream out = connection.getOutputStream()) {
                responseBodRequest400(new DataOutputStream(out));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) {
        try {
            responseStartLine(dos, httpResponse.responseLine());
            responseHeader(dos, httpResponse.getHeader());
            responseBody(dos, httpResponse.getBody());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseStartLine(DataOutputStream dos, String responseLine) throws IOException {
        dos.writeBytes(responseLine + " \r\n");
        logger.debug(responseLine);
    }

    private void responseHeader(DataOutputStream dos, HttpHeader httpHeader) throws IOException {
        for (String key : httpHeader.keySet()) {
            dos.writeBytes(key + ": " + httpHeader.getHeader(key) + "\r\n");
            logger.debug(key + ": " + httpHeader.getHeader(key));

        }
        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest parseRequest(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            line = br.readLine();
            logger.debug(line);
            HttpRequest httpRequest = new HttpRequest(line);
            while (!line.equals("")) {
                line = br.readLine();
                logger.debug(line);
            }

            return httpRequest;
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        throw new IllegalStateException();
    }


}
