package view;

import http.response.Response;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static view.ReturnType.FILE;
import static view.ReturnType.REDIRECT;
import static view.ReturnType.TEMPLATE;

public class ViewHandler {
    private static final String TEMPLATE_PATH = "./templates";
    private static final String STATIC_PATH = "./static";

    private ReturnType type;
    private String view;
    private String cookie;

    public void addCookie(String cookie) {
        this.cookie = cookie;
    }

    public void redirect(String url) {
        this.type = REDIRECT;
        this.view = url;
    }

    public void returnFile(String file) {
        this.type = FILE;
        this.view = file;
    }

    public void addTemplate(String template) {
        this.type = TEMPLATE;
        this.view = template;
    }

    public boolean isFile() {
        return type == FILE;
    }

    public boolean isTemplate() {
        return type == TEMPLATE;
    }

    public Response handle() throws IOException, URISyntaxException {
        byte[] bytes;
        if (isFile()) {
            bytes = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + view);
            Response response = Response.ofOk(bytes);
            response.putCookie(cookie + "; Path=/");
            return response;
        }

        if (isTemplate()) {
            bytes = view.getBytes();
            return Response.ofOk(bytes);
        }

        return Response.ofFound(view);

    }
}
