package webserver;

import endpoint.RequestEndpointRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;
import webserver.http.HttpRequestMessage;
import webserver.http.request.HttpRequestMessageParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

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
            List<String> httpRequestLines = IOUtils.readLines(br);
            HttpRequestMessage httpRequestMessage = HttpRequestMessageParser.parse(httpRequestLines);

            String httpPath = httpRequestMessage.httpPath();
            RequestEndpointRegistry.getEndpoint(httpPath);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = FileIoUtils.loadFileFromClasspath("./templates" + httpRequestMessage.httpPath());
            ResponseHandler.response200Header(dos, body.length);
            ResponseHandler.responseBody(dos, body);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
