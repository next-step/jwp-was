package web;

import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.*;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceHttpRequestHandlerTest {

    @Test
    public void handleResourceTest() throws IOException, URISyntaxException {
        HttpRequest httpRequest = HttpRequest.from(new BufferedReader(new StringReader(
                "GET /css/test-style.css HTTP/1.1\r\n" +
                "Accept: text/css\r")));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = HttpResponse.from(new DataOutputStream(byteArrayOutputStream));

        String result = "HTTP/1.1 200 OK\r\n" +
                "Accept: text/css\r\n" +
                System.lineSeparator() +
                new String(FileIoUtils.loadFileFromClasspath("./static/" + httpRequest.getPath()));

        ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
        resourceHttpRequestHandler.handleRequest(httpRequest, httpResponse);

        assertThat(new String(byteArrayOutputStream.toByteArray())).isEqualTo(result);
    }
}
