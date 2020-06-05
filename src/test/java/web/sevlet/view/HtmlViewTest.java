package web.sevlet.view;

import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import web.sevlet.view.HtmlView;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


public class HtmlViewTest {

    @Test
    public void initTest() {
        HtmlView htmlView = new HtmlView("./templates/index.html");
    }

    @Test
    public void renderTest() throws IOException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.from(new BufferedReader(new StringReader("GET /index.html HTTP/1.1")));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");

        String result = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=utf-8\r\n" +
                "Content-Length: " + body.length + "\r\n" +
                System.lineSeparator() +
                new String(body);

        HtmlView htmlView = new HtmlView("./templates/index.html");
        htmlView.render(new HashMap<>(), httpRequest, httpResponse);

        String httpResult = new String(byteArrayOutputStream.toByteArray());

        assertThat(httpResult).isEqualTo(result);
    }
}
