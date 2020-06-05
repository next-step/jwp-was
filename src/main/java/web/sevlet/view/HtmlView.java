package web.sevlet.view;

import http.HttpRequest;
import http.HttpResponse;
import org.springframework.util.StringUtils;
import utils.FileIoUtils;
import web.sevlet.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class HtmlView implements View {

    private String viewName;
    private byte[] body;

    public HtmlView(String viewName) {
        this.viewName = viewName;
        this.body = new byte[]{};

        if(StringUtils.isEmpty(viewName)) {
            throw new NullPointerException("viewName must have value");
        }
    }

    @Override
    public void render(Map<String, ?> model, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

        if(viewName.contains("redirect:")) {
            httpResponse.sendRedirect(viewName.split("redirect:")[1]);
            return;
        }

        try {
            body = FileIoUtils.loadFileFromClasspath(viewName);
        } catch (URISyntaxException e) {
            throw new IOException(e.getMessage(), e);
        }

        httpResponse.setContentType("text/html; charset=utf-8");
        httpResponse.setContentLength(body.length);

        httpResponse.writeHeader();
        writeBody(httpResponse);
    }

    private void writeBody(HttpResponse httpResponse) throws IOException {
        DataOutputStream dataOutputStream = httpResponse.getDataOutputStream();

        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();
    }
}
