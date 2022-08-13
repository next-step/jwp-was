package webserver.http.model.response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.model.request.Extension;
import webserver.http.model.request.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private final DataOutputStream dataOutputStream;
    private StatusLine statusLine;
    private ResponseHeaders responseHeaders;
    private String responseBody;
    private Cookie cookie;

    public HttpResponse(OutputStream outputStream) {
        dataOutputStream = new DataOutputStream(outputStream);
        responseHeaders = new ResponseHeaders();
    }

    public void addHeader(String key, String value) {
        responseHeaders.getResponseHeaderMap().put(key, value);
    }

    public void forward(String forwardPath) {
        statusLine = new StatusLine(StatusCode.OK);
        forwardBody(forwardPath);
        processHeaders();
    }

    public void forwardBody(String forwardPath) {
        responseBody = forwardPath;
    }

    public void responseStaticResource(HttpRequest httpRequest, byte[] body) {
        responseHeader(httpRequest, body);
        responseBody(body);
    }

    public void responseHeader(HttpRequest httpRequest, byte[] body) {
        if (Extension.CSS == Extension.getEnum(httpRequest.getPath())) {
            responseHeader(StatusCode.OK, ContentType.CSS, body.length);
            return;
        }
        response200Header(body.length);
    }

    private void response200Header(int lengthOfBodyContent) {
        responseHeader(StatusCode.OK, ContentType.HTML, lengthOfBodyContent);
    }

    private void responseHeader(StatusCode statusCode, ContentType contentType, int lengthOfBodyContent) {
        statusLine = new StatusLine(statusCode);
        responseHeaders.getResponseHeaderMap().put("Content-Type", contentType.getType() + ";charset=utf-8");
        responseHeaders.getResponseHeaderMap().put("Content-Length", String.valueOf(lengthOfBodyContent));
        processHeaders();
    }

    public void responseBody(byte[] body) {
        try {
            dataOutputStream.write(body, 0, body.length);
            dataOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendRedirect(String redirectPath) {
        statusLine = new StatusLine(StatusCode.FOUND);
        responseHeaders.getResponseHeaderMap().put("Location", redirectPath);
        processHeaders();
    }

    public void movePage(Object handlerMapping) throws IOException {
        if (handlerMapping instanceof Model) {
            movePageForModelType((Model) handlerMapping);
            return;
        }
        sendRedirect(String.valueOf(handlerMapping));
    }

    private void movePageForModelType(Model model) throws IOException {
        if (model.getModelMap() == null) {
            sendRedirect(String.valueOf(model.getPath()));
            return;
        }
        movePageWithModel(model);
    }

    private void movePageWithModel(Model model) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(model.getPath());

        String page = template.apply(model.getModelMap());
        byte[] body = page.getBytes(StandardCharsets.UTF_8);
        response200Header(body.length);
        responseBody(body);
    }

    public void processHeaders() {
        try {
            dataOutputStream.writeBytes(statusLine.statusLineText());
            for (String key : responseHeaders.getResponseHeaderMap().keySet()) {
                dataOutputStream.writeBytes(key + ": " + responseHeaders.getResponseHeaderMap().get(key) + " \r\n");
            }
            if (cookie != null) {
                dataOutputStream.writeBytes(cookie + "\r\n");
            }
            dataOutputStream.writeBytes("\r\n");
            if (responseBody != null) {
                dataOutputStream.writeBytes(responseBody);
                dataOutputStream.writeBytes("\r\n");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void addCookie(Cookie cookie) {
        this.cookie = cookie;
    }
}
