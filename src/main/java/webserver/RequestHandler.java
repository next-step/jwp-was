package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.ContentType;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

import java.io.*;
import java.net.Socket;

import static utils.IOUtils.readData;
import static utils.IOUtils.readLines;
import static webserver.WebContext.CONTROLLERS;
import static webserver.WebContext.HTTP_SESSION_MANAGER;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private DispatcherController dispatcherController;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.dispatcherController = new DispatcherController();
        dispatcherController.setMappingRegistry(CONTROLLERS);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = readStream(in);
            dispatcherController.dispatch(httpRequest, new HttpResponse(dos));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        RequestLine requestLine = RequestLine.parse(reader.readLine());
        RequestHeader requestHeader = RequestHeader.from(readLines(reader));

        ContentType contentType = requestHeader.getContentType();
        RequestBody requestBody = new RequestBody(contentType
                .to(readData(reader, requestHeader.getContentLength())));

        return new HttpRequest(requestLine, requestHeader, requestBody, HTTP_SESSION_MANAGER);
    }

}
