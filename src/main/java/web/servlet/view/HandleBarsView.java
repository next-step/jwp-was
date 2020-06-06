package web.servlet.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import http.HttpRequest;
import http.HttpResponse;
import org.springframework.util.StringUtils;
import web.servlet.View;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HandleBarsView implements View {

    private String viewName;
    private Handlebars handlebars;
    private byte[] body;

    public HandleBarsView(String viewName, Handlebars handlebars) {
        this.viewName = viewName;
        this.handlebars = handlebars;
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

        Template template = handlebars.compile(viewName);
        body = template.apply(model).getBytes();

        httpResponse.setContentType("text/html");
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
