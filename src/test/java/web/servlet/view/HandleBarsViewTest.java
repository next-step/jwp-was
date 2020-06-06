package web.servlet.view;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import http.HttpHeaders;
import http.HttpRequest;
import http.HttpResponse;
import http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import utils.HttpStringBuilder;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


public class HandleBarsViewTest {

    private Handlebars handlebars;

    @BeforeEach
    public void initHandleBars() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        this.handlebars = new Handlebars(loader);
    }

    @Test
    public void initTest() {
        HandleBarsView handleBarsView = new HandleBarsView("index", handlebars);
    }

    @Test
    public void renderTest() throws IOException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.from(new BufferedReader(new StringReader("GET /test_view.html HTTP/1.1")));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));
        byte[] body = FileIoUtils.loadFileFromClasspath("templates/test_view.html");

        String result = HttpStringBuilder.builder()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML)
                .addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(body.length))
                .body(new String(body))
                .buildResponse();

        HandleBarsView handleBarsView = new HandleBarsView("test_view", handlebars);
        handleBarsView.render(new HashMap<>(), httpRequest, httpResponse);

        String httpResult = new String(byteArrayOutputStream.toByteArray());

        assertThat(httpResult).isEqualTo(result);
    }
}
