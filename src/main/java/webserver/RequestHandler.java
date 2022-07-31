package webserver;

import exception.ResourceNotFoundException;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatusCode;
import model.ResponseHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import utils.IOUtils;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATE_PATH = "./templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            List<String> lines = IOUtils.readLines(in);
            HttpRequest request = HttpRequest.of(lines);

            HttpResponse httpResponse = handle(request);

            writeHttpResponse(httpResponse, dos);
        } catch (IOException | URISyntaxException | ResourceNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    private HttpResponse handle(HttpRequest request) throws IOException, URISyntaxException{
        String path = addTemplatePath(request.getPath());
        byte[] bytes = FileIoUtils.loadFileFromClasspath(path);
        return HttpResponse.ok(HttpStatusCode.OK, ResponseHeader.textHtml(bytes.length), bytes);
    }

    private String addTemplatePath(String path) {
        return TEMPLATE_PATH + path;
    }

    private void writeHttpResponse(HttpResponse httpResponse, DataOutputStream dos)  throws IOException {
        writeHttpHeaders(httpResponse, dos);
        dos.writeBytes("\r\n");
        writeBody(dos, httpResponse.getBody());
    }

    private void writeHttpHeaders(HttpResponse httpResponse, DataOutputStream dos) throws IOException {
        dos.writeBytes(String.format("HTTP/1.1 %s \r\n", httpResponse.getHttpResponseCode()));
        for (Map.Entry<String, Object> header : httpResponse.getHeaders()) {
            dos.writeBytes(String.format("%s: %s \r\n", header.getKey(), header.getValue()));
        }
    }

    private void writeBody(DataOutputStream dos, byte[] body) throws IOException{
        dos.write(body, 0, body.length);
        dos.flush();
    }

}
