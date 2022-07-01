package mvc.view;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.http.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class TemplateViewResolverTest {
    private String testDirectory = "./src/test/resources/";

    private HttpRequest request;

    @BeforeEach
    void setUp() throws Exception {
        request = request("HTTP_GET_with_QueryString.txt");
    }

    @Test
    void render() throws Exception {
        List<User> users = Arrays.asList(
                new User("javajigi", "password", "자바지기", "javajigi@slipp.net"),
                new User("sanjigi", "password", "산지기", "sanjigi@slipp.net")
        );
        request.addAttribute("users", users);
        HttpResponse response = new HttpResponse(createOutputStream("list.html"));
        TemplateViewResolver viewResolver = new TemplateViewResolver();
        View view = viewResolver.resolveViewName("list");
        view.render(request, response);
    }

    private HttpRequest request(String fileName) throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + fileName));
        HttpRequestPipe httpRequestPipe = new CachedHttpRequest(new BufferedReaderAsHttpRequest(in));
        return httpRequestPipe.request();
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}
