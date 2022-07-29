package webserver.response;

import exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import webserver.Header;
import webserver.Cookie;
import webserver.ViewResolver;
import webserver.controller.PathMapping;
import webserver.request.HttpRequest;
import webserver.request.Model;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class HttpResponse {
    private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

    private HttpStatus httpStatus;
    private byte[] body;
    private Cookie cookie;
    private Header header;
    private String locationPath;
    private Model model;

    public HttpResponse(HttpStatus httpStatus, byte[] body) {
        this.httpStatus = httpStatus;
        this.header = new Header();
        this.body = body;
        this.model = new Model();
    }

    public HttpResponse() {
        cookie = new Cookie();
        header = new Header();
        this.model = new Model();
    }

    public void sendStatus(DataOutputStream dos) {
        ResponseWriter.findResponseBodyWrite(this.httpStatus)
                .ifPresent(responseWrite -> responseWrite.write(this, dos));
    }

    public void start(HttpRequest request, DataOutputStream dos) {
        HttpResponse response = new HttpResponse();

        HttpResponse httpResponse = FileResponse.redirect(request.getRequestPath())
                .orElse(getApiHttpResponse(request, response));
        httpResponse.sendStatus(dos);
    }

    private HttpResponse getApiHttpResponse(HttpRequest request, HttpResponse response) {
        try {
            PathMapping.findMappingUrl(request)
                    .mappingController(request, response);
        } catch (NotFoundException e) {
            logger.error("error {}", e.getMessage());
            return notFoundRedirect(response, "/error/not_found.html");
        }

        return response;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodyLength() {
        return body.length;
    }

    public void makeStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public String getLocationPath() {
        return locationPath;
    }

    public void makeLocationPath(String path) {
        this.locationPath = path;
    }

    public void addModel(Model model) {
        this.model = model;
    }

    public Map<String, Object> getModelMap() {
        return model.getModelMap();
    }

    public void addBody(byte[] body) {
        this.body = body;
    }

    public Header getHeader() {
        return header;
    }

    public void forward(String path) {
        FileResponse.redirect(path);
    }

    public void sendRedirect(HttpRequest request, HttpResponse response) {
        response.makeStatus(HttpStatus.FOUND);
    }

    public void forward(HttpResponse response, String path) throws IOException {
        response.makeStatus(HttpStatus.OK);
        response.addBody(ViewResolver.mapping(response, path).getBytes());
    }

    public HttpResponse notFoundRedirect(HttpResponse response, String path) {
        response.makeStatus(HttpStatus.NOT_FOUND);
        try {
            response.addBody(FileIoUtils.loadFileFromClasspath(path));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return response;
    }
}