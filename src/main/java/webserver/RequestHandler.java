package webserver;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.Service;
import utils.IOUtils;
import webserver.handler.ApiHandler;
import webserver.handler.Handler;
import webserver.handler.ResourceHandler;
import webserver.request.Headers;
import webserver.request.RequestBody;
import webserver.request.RequestLine;
import webserver.response.Response;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private static final List<Handler> handlers = Lists.newArrayList(new ApiHandler(), new ResourceHandler());

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            RequestLine requestLine = RequestLine.from(line);
            Headers headers = getHeaders(br);

            int contentLength = headers.getContentLength();
            String body = IOUtils.readData(br, contentLength);
            logger.debug("Body: {}", body);
            RequestBody requestBody = RequestBody.from(body);

            Response response = Response.response202();
            for (Handler handler : handlers) {
                Service service = handler.find(requestLine);
                if (Objects.nonNull(service)) {
                    response = service.doService(requestLine, requestBody);
                    break;
                }
            }

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, response);
            responseBody(dos, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Headers getHeaders(BufferedReader br) throws IOException {
        String line = null;
        Headers headers = new Headers();
        while (!"".equals(line)) {
            logger.debug("{}", line);
            line = br.readLine();
            if (Objects.isNull(line)) {
                break;
            }

            headers.addHeaderByLine(line);
        }
        return headers;
    }

    private void response200Header(DataOutputStream dos, Response response) {
        try {
            dos.writeBytes(String.format("HTTP/1.1 %s OK \r\n", response.getCode()));
            dos.writeBytes(String.format("Location: %s\r\n", response.getLocation()));
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes(String.format("Set-Cookie: %s Path=/\r\n", response.parseCookie()));
            dos.writeBytes("Content-Length: " + response.getBody().length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, Response response) {
        try {
            byte[] body = response.getBody();
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
