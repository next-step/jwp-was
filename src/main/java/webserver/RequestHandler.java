package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.RequestLine;
import webserver.http.RequestHeader;
import webserver.service.WebService;
import webserver.service.WebServiceFactory;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            if (in == null) {
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            RequestLine requestLine = readRequestLine(br);
            RequestHeader requestHeader = readRequestHeader(br);

            handleRequestBody(requestLine, br, requestHeader.findByKey("Content-Length"));
            handlerWebService(requestLine);

            ResponseHandler.response(out, requestLine);
        } catch (IOException| URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void handleRequestBody(RequestLine requestLine, BufferedReader br, String contentLength) throws IOException {
        if ("GET".equals(requestLine.getMethod())) {
            return ;
        }

        String reqBody = IOUtils.readData(br, Integer.parseInt(contentLength));
        requestLine.getPath().addParameters(reqBody);
    }

    private void handlerWebService(RequestLine requestLine) {
        WebService webService = WebServiceFactory.create(requestLine.getPath().getPath());
        if (webService != null) {
            webService.process(requestLine);
        }
    }

    public RequestLine readRequestLine(BufferedReader br) throws IOException {
        return RequestLine.parse(br.readLine());
    }

    public RequestHeader readRequestHeader(BufferedReader br) throws IOException {
        return RequestHeader.newInstance(br);
    }
}
