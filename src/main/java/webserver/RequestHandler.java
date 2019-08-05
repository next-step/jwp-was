package webserver;

import enums.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpBaseRequest;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.mapper.RequestMappers;
import webserver.resolvers.body.BodyResolvers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class RequestHandler implements Runnable {


    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final String TEMPLATE_ROOT = "./templates";

    private Socket connection;
    private RequestMappers requestMappers;
    private BodyResolvers bodyResolvers;

    public RequestHandler(Socket connectionSocket, RequestMappers requestMappers, BodyResolvers bodyResolvers) {
        this.connection = connectionSocket;
        this.requestMappers = requestMappers;
        this.bodyResolvers = bodyResolvers;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = bodyResolvers.resoveByMatchResolver(HttpBaseRequest.parse(in));
            HttpResponse httpResponse = HttpResponse.of(httpRequest);
            requestMappers.matchHandle(httpRequest, httpResponse);
            writeResponse(new DataOutputStream(out), httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeResponse(DataOutputStream dos, HttpResponse httpResponse) {
        writeStatusLine(dos, httpResponse.getHttpStatus());
        writeHeaderLines(dos, httpResponse.getHttpHeaderLines());
        writeBody(dos, httpResponse.getResponseBody());
        writeFlush(dos);
    }

    private void writeStatusLine(DataOutputStream dos, HttpStatus httpStatus) {
        try {
            dos.writeBytes("HTTP/1.1 " + httpStatus.getValue() + " " + httpStatus.getReasonPhrase() + " \r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaderLines(DataOutputStream dos, List<String> headerLines) {
        try {
            for (String headerLine : headerLines) {
                dos.writeBytes(headerLine + " \r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeBody(DataOutputStream dos, byte[] body) {
        if (body == null) {
            return;
        }

        try {
            dos.write(body, 0, body.length);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void writeFlush(DataOutputStream dos) {
        try {
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
