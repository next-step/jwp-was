package webserver;

import webserver.customhandler.LoginHandler;
import webserver.customhandler.StaticResourceHandler;
import webserver.customhandler.TemplateHandler;
import webserver.customhandler.UserCreateHandler;

public class HandlerRegister {
    public static void register(){
        //등록할 Handler를 직접 타이핑해야 합니다.
        Handlers.addHandler(new LoginHandler("/login"));
        Handlers.addHandler(new UserCreateHandler("/users"));
        Handlers.addHandler(new TemplateHandler());
        Handlers.addHandler(new StaticResourceHandler());
    }
}
