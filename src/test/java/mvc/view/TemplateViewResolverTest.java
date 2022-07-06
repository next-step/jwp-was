package mvc.view;

import org.junit.jupiter.api.BeforeEach;
import users.model.User;
import org.junit.jupiter.api.Test;
import was.WasTestTemplate;
import was.http.HttpRequest;
import was.http.HttpResponse;

import java.util.Arrays;
import java.util.List;

public class TemplateViewResolverTest {
    private WasTestTemplate testTemplate;

    @BeforeEach
    void setUp() {
        testTemplate = new WasTestTemplate();
    }

    @Test
    void render() throws Exception {
        HttpRequest request = testTemplate.request("http/HTTP_GET_without_QueryString.txt");
        List<User> users = Arrays.asList(
                new User("javajigi", "password", "자바지기", "javajigi@slipp.net"),
                new User("sanjigi", "password", "산지기", "sanjigi@slipp.net")
        );
        request.addAttribute("users", users);
        HttpResponse response = new HttpResponse(testTemplate.createOutputStream("template.html"));
        TemplateViewResolver viewResolver = new TemplateViewResolver();
        View view = viewResolver.resolveViewName("list");
        view.render(request, response);
    }
}
