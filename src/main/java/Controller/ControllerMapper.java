package Controller;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerMapper {
    private ControllerMapper() {
    }

    private static final Map<String, Controller> controllerMap = new ConcurrentHashMap();

    static {
//        controllerMap.put("/user/create", CreateUserController.class);
//        controllerMap.put("/user/login", LoginUserController.class);
//        controllerMap.put("/user/list.html", ListUserController.class);

        // TODO 이렇게 되면 싱글톤이 되는데 컨트롤러가 상태를 가지게되면 문제가 생길 수 있다. class 넣고 객체 반환으로 고쳐야할 듯
        controllerMap.put("/", new IndexController());
        controllerMap.put("/user/create", new CreateUserController());
        controllerMap.put("/user/login", new LoginUserController());
        controllerMap.put("/user/list.html", new ListUserController());
    }

    @Nullable
    public static Controller getController(@Nonnull String path) {
        Controller controller = controllerMap.get(path);
        if (controller == null) {
            return null;
        }

        return controller;
    }
}
