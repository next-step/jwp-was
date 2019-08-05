package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpProcessor;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.request.RequestBody;
import webserver.http.request.RequestHeader;
import webserver.http.request.RequestLine;

import java.io.*;
import java.net.Socket;

import static utils.IOUtils.readLines;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpProcessor httpProcessor;
    public static final String WELCOME_PAGE = "/index.html";

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpProcessor = new HttpProcessor();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = createRequestHolder(in);
            httpProcessor.process(httpRequest, new HttpResponse(dos, httpRequest));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpRequest createRequestHolder(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        RequestLine requestLine = RequestLine.parse(reader.readLine());
        RequestHeader requestHeader = RequestHeader.parse(readLines(reader));
        RequestBody requestBody = RequestBody.parse(reader, requestHeader);

        return new HttpRequest(requestLine, requestHeader, requestBody);
    }


}
