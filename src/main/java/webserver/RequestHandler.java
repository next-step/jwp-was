package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import controller.Controller;
import model.HttpHeader;
import model.HttpRequest;
import model.HttpResponse;
import model.RequestMappingInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final RequestMapper mapper;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
        this.mapper = new RequestMapper();
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            final HttpHeader httpHeader = new HttpHeader(IOUtils.readHeaderData(br));
            final RequestLine requestLine = new RequestLine(httpHeader.getRequestLine());
            final String requestBody = IOUtils.readData(br, httpHeader.getContentLength());
            final HttpRequest httpRequest = new HttpRequest(requestLine, requestBody);

            Controller controller = mapper.mapping(new RequestMappingInfo(requestLine.getMethod(), requestLine.getRequestPath()));
            HttpResponse response = controller.process(httpRequest);
            writeResponse(dos, response);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse response) {
        try {
            for (String message: response.getMessages()) {
                dos.writeBytes(message);
            }
            if (response.hasBody()) {
                dos.write(response.getBody(), 0, response.getBody().length);
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
