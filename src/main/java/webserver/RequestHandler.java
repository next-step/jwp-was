package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String TEMPLATES_PREFIX = "/templates";
    private static final String HTML_FILE_SUFFIX = ".html";
    private static final String ERROR_TEMPLATES_PREFIX = "error";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = HttpRequest.parse(new BufferedReader(new InputStreamReader(in)));
            logger.debug("Request {}", httpRequest.getRequestLine());

            HttpResponse httpResponse = getResponse(httpRequest);
            httpResponse.responseByStatus(dos);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    HttpResponse getResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        String requestPath = httpRequest.getUri().getPath();
        if (requestPath.endsWith(HTML_FILE_SUFFIX))
            return new HttpResponse(200, FileIoUtils.loadFileFromClasspath("." + TEMPLATES_PREFIX + requestPath));

        HttpResponse httpResponse = new HttpResponse();
        String viewName = getViewName(httpRequest, httpResponse);
        if (viewName.startsWith("redirect:")) {
            String redirectPath = String.format("http://%s%s", httpRequest.getHeaders().get("Host"), viewName.substring(viewName.indexOf(":") + 1));
            httpResponse.setRedirectPath(redirectPath);
            httpResponse.setStatusCode(302);
            return httpResponse;
        }

        byte[] body = viewMapping(httpRequest, httpResponse).getBytes();
        httpResponse.setBody(body);
        return httpResponse;
    }

    private String getViewName(HttpRequest httpRequest, HttpResponse httpResponse) {
        return Router.route(httpRequest, httpResponse).orElse("");
    }

    private String viewMapping(HttpRequest request, HttpResponse response) throws IOException {
        String path = Router.route(request, response).orElse("");
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix(TEMPLATES_PREFIX);
        loader.setSuffix(HTML_FILE_SUFFIX);
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(path);

        if (path.startsWith("/" + ERROR_TEMPLATES_PREFIX) || path.startsWith(ERROR_TEMPLATES_PREFIX))
            return template.text();

        return template.apply(response.getModel().getModelMap());
    }
}
