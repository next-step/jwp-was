package mvc;

import model.User;
import mvc.controller.AbstractController;
import mvc.view.TemplateViewResolver;
import mvc.view.View;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.util.Arrays;
import java.util.List;

public class FakeUsersController extends AbstractController {
    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        List<User> users = Arrays.asList(
                new User("javajigi", "password", "자바지기", "javajigi@slipp.net"),
                new User("sanjigi", "password", "산지기", "sanjigi@slipp.net")
        );
        request.addAttribute("users", users);

        TemplateViewResolver viewResolver = new TemplateViewResolver();
        View view = viewResolver.resolveViewName("list");
        view.render(request, response);
    }
}
