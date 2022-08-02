package slipp.config;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import slipp.controller.LoginController;
import slipp.controller.UserCreateController;
import slipp.controller.UserListController;
import slipp.service.UserCreateService;
import webserver.WebApplicationServer;
import webserver.http.domain.controller.Controller;
import webserver.http.domain.controller.template.TemplateCompiler;

import java.util.List;

public class SlippConfig {
    private static List<Controller> controllers() {
        return List.of(
                new LoginController(),
                new UserCreateController(userCreateService()),
                new UserListController(templateCompiler())
        );
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
