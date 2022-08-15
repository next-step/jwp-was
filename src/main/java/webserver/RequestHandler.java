package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

import controller.Controller;
import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
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
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            final DataOutputStream dataOutputStream = new DataOutputStream(out);

            final RequestLine requestLine = new RequestLine(IOUtils.readRequestData(bufferedReader));
            final HttpHeader httpHeader = new HttpHeader(IOUtils.readHeaderData(bufferedReader));
            final RequestBody body = new RequestBody(IOUtils.readData(bufferedReader, httpHeader.getValueToInt("Content-Length")));

            final HttpRequest httpRequest = new HttpRequest(httpHeader, requestLine, body);
            final HttpResponse response = new HttpResponse();

            logger.debug("request : {}", httpRequest);

            Controller controller = mapper.mapping(new RequestMappingInfo(httpRequest));
            controller.service(httpRequest, response);
            response.writeResponse(dataOutputStream);

        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
