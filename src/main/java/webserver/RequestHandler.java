package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import controller.Controller;
import controller.RequestMapping;
import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import http.QueryStrings;
import http.RequestLineParser;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import javax.servlet.http.HttpSession;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);
            String path = httpRequest.getPath();
            logger.debug("path : {} ", path);

            Controller controller = RequestMapping.getController(path);

            if (controller == null) {
                if (path.equals("/")) {
                    path = "/index.html";
                }
                httpResponse.forward(path);
            } else {
                controller.service(httpRequest, httpResponse);
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
