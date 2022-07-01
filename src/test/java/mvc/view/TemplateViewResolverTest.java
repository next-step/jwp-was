package mvc.view;

import model.User;
import org.junit.jupiter.api.Test;
import was.WasBaseTest;
import was.http.HttpRequest;
import was.http.HttpResponse;

import java.util.Arrays;
import java.util.List;

public class TemplateViewResolverTest extends WasBaseTest {
    @Test
    void render() throws Exception {
        HttpRequest request = request("http/HTTP_GET_without_QueryString.txt");
        List<User> users = Arrays.asList(
                new User("javajigi", "password", "자바지기", "javajigi@slipp.net"),
                new User("sanjigi", "password", "산지기", "sanjigi@slipp.net")
        );
        request.addAttribute("users", users);
        HttpResponse response = new HttpResponse(createOutputStream("template.html"));
        TemplateViewResolver viewResolver = new TemplateViewResolver();
        View view = viewResolver.resolveViewName("list");
        view.render(request, response);
    }
}
