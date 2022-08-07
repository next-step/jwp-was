package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import cookie.Cookie;
import webserver.http.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            HttpRequest httpRequest = new HttpRequest(bufferedReader);

            DataOutputStream dos = new DataOutputStream(out);
            movePage(httpRequest, dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void movePage(HttpRequest httpRequest, DataOutputStream dos) throws IOException, URISyntaxException {
        if (httpRequest.isStaticResource()) {
            byte[] body = FileIoUtils.loadFileFromClasspath(httpRequest.responsePath());
            response200Header(dos, body.length);
            responseBody(dos, body);
            return;
        }
        Object handlerMapping = HandlerAdapter.handlerMapping(httpRequest);
        moveNotStaticResourcePage(dos, handlerMapping);
    }

    private void moveNotStaticResourcePage(DataOutputStream dos, Object handlerMapping) throws IOException {
        if (handlerMapping instanceof Model) {
            movePageForModelType(dos, (Model) handlerMapping);
            return;
        }
        response302Header(dos, String.valueOf(handlerMapping));
    }

    private void movePageForModelType(DataOutputStream dos, Model model) throws IOException {
        if (model.getModelMap() == null) {
            response302Header(dos, String.valueOf(model.getPath()));
            return;
        }
        movePageWithModel(dos, model);
    }

    private void movePageWithModel(DataOutputStream dos, Model model) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(model.getPath());

        String page = template.apply(model.getModelMap());
        byte[] body = page.getBytes(StandardCharsets.UTF_8);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            if (Cookie.exists()) {
                dos.writeBytes(Cookie.getResponseCookie() + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String redirectPath) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: " + redirectPath + " \r\n");
            if (Cookie.exists()) {
                dos.writeBytes(Cookie.getResponseCookie() + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
