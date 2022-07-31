package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import constant.HttpContentType;
import constant.HttpHeader;
import controller.Controller;
import controller.UserCreateController;
import java.io.*;
import java.net.Socket;

import java.util.List;
import java.util.Map;
import request.HttpRequest;
import request.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import response.HttpResponse;
import utils.FileIoUtils;
import utils.IOUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            DataOutputStream dos = new DataOutputStream(connection.getOutputStream()); ) {

            HttpRequest request = HttpRequest.from(IOUtils.readLines(br));
            request.addBody(IOUtils.readData(br, request.getContentLength()));

            Controller controller = DispatcherServlet.isMatcher(request.getPath());
            HttpResponse response = controller.service(request);

            writeResponse(response, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    private void writeResponse(HttpResponse response, DataOutputStream dos) {
        try {
            dos.writeBytes(String.format("HTTP/1.1 %s \r\n", response.getResponseCode() ));
            writeHeaders(response, dos);
            dos.writeBytes("\r\n");
            dos.write(response.getBody(), 0, response.getBody().length);
            dos.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void writeHeaders(HttpResponse response, DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> header : response.getHeaderEntiry()) {
            dos.writeBytes(String.format("%s: %s\r\n", header.getKey(), header.getValue()));
        }
    }
}
