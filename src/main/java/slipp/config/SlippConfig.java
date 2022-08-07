package slipp.config;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import slipp.controller.LoginController;
import slipp.controller.UserCreateController;
import slipp.controller.UserListController;
import slipp.service.AuthenticateService;
import slipp.service.UserCreateService;
import webserver.WebApplicationServer;
import webserver.http.controller.Controller;
import webserver.http.controller.template.TemplateCompiler;

import java.util.List;

public class SlippConfig {
    private static List<Controller> controllers() {
        return List.of(
                new LoginController(authenticateService()),
                new UserCreateController(userCreateService()),
                new UserListController(templateCompiler())
        );
    }

    private static AuthenticateService authenticateService() {
        return new AuthenticateService();
    }

    private static UserCreateService userCreateService() {
        return new UserCreateService();
    }

    private static TemplateCompiler templateCompiler() {
        return new TemplateCompiler(templateLoader());
    }

    private static TemplateLoader templateLoader() {
        return new ClassPathTemplateLoader("/templates", ".html");
    }

    public static WebApplicationServer webApplicationServer() {
        return new WebApplicationServer(controllers());
    }
}
