package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestBody;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;
import webserver.request.RequestLine;
import webserver.response.ResponseHolder;
import webserver.servlet.RegistrationServlet;

import java.io.*;
import java.net.Socket;

import static com.google.common.collect.ImmutableList.of;
import static utils.IOUtils.readLines;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpProcessor httpProcessor;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.httpProcessor = new HttpProcessor(of(new RegistrationServlet()));
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            RequestHolder requestHolder = createRequestHolder(in);
            httpProcessor.process(requestHolder, new ResponseHolder(dos, requestHolder));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestHolder createRequestHolder(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        RequestLine requestLine = RequestLine.parse(reader.readLine());
        RequestHeader requestHeader = RequestHeader.parse(readLines(reader));
        RequestBody requestBody = RequestBody.parse(reader, requestHeader);

        return new RequestHolder(requestLine, requestHeader, requestBody);
    }


}
