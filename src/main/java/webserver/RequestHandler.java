package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.domain.*;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String url = br.readLine();
            RequestHeader requestHeader = new RequestHeader();
            requestHeader.addRequestHeaders(br);
            RequestBody requestBody = body(br, requestHeader);
            RequestMapping requestMapping = new RequestMapping();

            HttpRequest httpRequest = new HttpRequest(requestHeader, new RequestLine(url), requestBody);
            byte[] body;
            DataOutputStream dos = new DataOutputStream(out);
            Controller controller = requestMapping.controller(httpRequest.path());
            HttpResponse httpResponse = new HttpResponse(dos);
            if (controller != null) {
                controller.execute(httpRequest, httpResponse);
            }

            if (httpRequest.endsWith("css")) {
                body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.path());
                responseCssHeader(dos, body.length);
                responseBody(dos, body);
            } else if (httpRequest.endsWith("js") || httpRequest.startsWith("/fonts")) {
                body = FileIoUtils.loadFileFromClasspath("./static" + httpRequest.path());
                responseBody(dos, body);
            }

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestBody body(BufferedReader br, RequestHeader requestHeader) throws IOException {
        String value = requestHeader.getValue("Content-Length");

        if (StringUtils.hasText(value)) {
            String body = IOUtils.readData(br, Integer.parseInt(value));
            return new RequestBody(decode(body));
        }

        return new RequestBody();
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private void responseCssHeader(DataOutputStream dos, int contentLength) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css\r\n");
            dos.writeBytes("Content-Length: " + contentLength + "\r\n");
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
