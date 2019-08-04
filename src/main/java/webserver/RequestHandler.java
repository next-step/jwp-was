package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;
import webserver.request.RequestLine;
import webserver.servlet.RegistrationServlet;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ResponseHandler responseHandler;
    private ServletProcessor servletProcessor;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.responseHandler = new ResponseHandler();
        this.servletProcessor = new ServletProcessor(of(new RegistrationServlet()));
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            RequestHolder requestHolder = createRequestHolder(in);
            servletProcessor.process(requestHolder);
            responseHandler.handle(dos, requestHolder);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private RequestHolder createRequestHolder(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<String> requestHeaders = new ArrayList<>();
        RequestLine requestLine = RequestLine.parse(reader.readLine());

        String header;
        while (StringUtils.isNotBlank(header = reader.readLine())) {
            requestHeaders.add(header);
        }

        return new RequestHolder(requestLine, RequestHeader.parse(requestHeaders));
    }


}
