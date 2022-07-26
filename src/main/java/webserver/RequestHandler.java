package webserver;

import endpoint.Endpoint;
import endpoint.HttpRequestEndpointHandler;
import endpoint.RequestEndpointRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequestMessage;
import webserver.http.response.HttpResponseMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), UTF_8));
                OutputStream out = connection.getOutputStream()
        ) {
            HttpRequestMessage httpRequestMessage = HttpLinesReader.readRequestMessage(br);

            String httpPath = httpRequestMessage.httpPath();

            Endpoint endpoint = new Endpoint(httpRequestMessage.httpMethod(), httpPath);
            HttpRequestEndpointHandler httpRequestEndpointHandler = RequestEndpointRegistry.getEndpoint(endpoint);

            HttpResponseMessage httpResponseMessage = httpRequestEndpointHandler.handle(httpRequestMessage);
            DataOutputStream dos = new DataOutputStream(out);

            ResponseHandler.handleResponse(dos, httpResponseMessage);

//            byte[] body;
//            if (httpRequestMessage.httpPath().contains(".html")) {
//                body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequestMessage.httpPath());
//            } else {
//                body = "Hello World".getBytes(UTF_8);
//            }
//            ResponseHandler.response200Header(dos, body.length);
//            ResponseHandler.responseBody(dos, body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
