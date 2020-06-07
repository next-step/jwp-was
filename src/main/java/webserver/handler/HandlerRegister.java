package webserver.handler;

import webserver.handler.custom.LoginHandler;
import webserver.handler.custom.UserCreateHandler;
import webserver.handler.custom.UserListHandler;

public class HandlerRegister {
    public static void register() {
        //등록할 Handler를 직접 타이핑해야 합니다.
        Handlers.addHandler(new LoginHandler("/login"));
        Handlers.addHandler(new UserCreateHandler("/users"));
        Handlers.addHandler(new UserListHandler("/user/list"));
    }
}
