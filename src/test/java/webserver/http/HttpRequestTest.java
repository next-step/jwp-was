package webserver.http;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestTest {

    private String testDirectory = "./src/test/resources/";

    @Test
    void request_resttemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        HttpRequest httpRequest = RequestFactory.create(new BufferedReader(new InputStreamReader(in, "UTF-8")));

        assertEquals("GET", httpRequest.getMethod().toString());
        assertEquals("/user/create", httpRequest.getPath());
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("javajigi", httpRequest.getParameter("userId"));
    }

    @Test
    public void request_POST1() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST1.txt");
        HttpRequest httpRequest = RequestFactory.create(new BufferedReader(new InputStreamReader(in, "UTF-8")));

        assertEquals("POST", httpRequest.getMethod().toString());
        assertEquals("/user/create", httpRequest.getPath());
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("javajigi", httpRequest.getParameter("userId"));
    }

    @Test
    public void request_POST2() throws Exception {
        InputStream in = new FileInputStream(testDirectory + "Http_POST2.txt");
        HttpRequest httpRequest = RequestFactory.create(new BufferedReader(new InputStreamReader(in, "UTF-8")));

        assertEquals("POST", httpRequest.getMethod().toString());
        assertEquals("/user/create", httpRequest.getPath());
        assertEquals("keep-alive", httpRequest.getHeader("Connection"));
        assertEquals("1", httpRequest.getParameter("id"));
        assertEquals("javajigi", httpRequest.getParameter("userId"));
    }
}
