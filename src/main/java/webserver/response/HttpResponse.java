package webserver.response;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private ResponseLine responseLine;
    private ResponseHeaders responseHeaders;
    private ResponseBody responseBody;

    public HttpResponse(ResponseHeaders responseHeaders, ResponseBody responseBody) {
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public static HttpResponse of(HttpRequest httpRequest) throws IOException, URISyntaxException {
        ResponseHeaders responseHeaders = ResponseHeaders.of();
        ResponseBody responseBody = ResponseBody.of(httpRequest.getRequestLine());
        responseHeaders.addHeader("Content-Type", responseBody.getContentType(), "charset=utf-8");
        responseHeaders.addHeader("Content-Length", responseBody.getLength());
        return new HttpResponse(responseHeaders, responseBody);
    }

    public static HttpResponse of() {
        ResponseHeaders responseHeaders = ResponseHeaders.of();
        ResponseBody responseBody = new ResponseBody();
        return new HttpResponse(responseHeaders, responseBody);
    }


    public byte[] getBody() {
        byte[] file = responseBody.getFile();
        if (Objects.isNull(file)) {
            return "".getBytes();
        }
        return file;
    }

    public void response200() {
        this.responseLine = ResponseLine.of("200", "OK");
    }

    public void response302(String location) {
        this.responseLine = ResponseLine.of("302", "FOUND");
        responseHeaders.addHeader("Location", location);
    }

    public Map<String, ResponseHeader> getResponseHeaders() {
        return responseHeaders.getResponseHeaders();
    }

    public String getResponseLine() {
        return responseLine.response();
    }

    public void addCookie(String value) {
        responseHeaders.addHeader("Set-Cookie", value, "Path=/");
    }

    public void addBody(Collection<User> users) {
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);
        String userListPage;
        try {
            Template template = handlebars.compile("user/list");
            userListPage = template.apply(model);
            responseBody.addFile(userListPage.getBytes());
        } catch (Exception e) {
            log.debug("handlebars error : {}", e.getMessage());
        }
    }
}
