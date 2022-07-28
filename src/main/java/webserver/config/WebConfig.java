package webserver.config;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import webserver.application.UserService;
import webserver.handlers.ControllerContainer;
import webserver.handlers.ControllerContainerImpl;
import webserver.ui.Controller;
import webserver.ui.UserController;
import webserver.ui.WelcomeController;

import java.util.HashMap;
import java.util.Map;

public class WebConfig {
    public static final String NO_SUCH_BEAN_EXCEPTION_MSG = "존재하지 않는 빈입니`다. :";
    private final Map<Class<?>, Object> store = new HashMap<>();

    public WebConfig() {
        addBean(controllerContainer());
    }

    public <T> T getBean(Class<T> type) {
        if (store.containsKey(type)) {
            return (T) store.get(type);
        }
        throw new NoSuchBeanDefinitionException(NO_SUCH_BEAN_EXCEPTION_MSG + type.getName());
    }

    protected void addBean(Object bean) {
        Class<?>[] beanTypes = bean.getClass().getInterfaces();
        if (beanTypes.length < 1) {
            throw new NoSuchBeanDefinitionException(NO_SUCH_BEAN_EXCEPTION_MSG + bean.getClass().getName());
        }

        store.put(beanTypes[0], bean);
    }


    private ControllerContainer controllerContainer() {
        ControllerContainerImpl controllerContainer = ControllerContainerImpl.newInstance();

        return controllerContainer.addController(welcomeController())
                .addController(userController());
    }

    private Controller welcomeController() {
        return new WelcomeController();
    }

    private Controller userController() {
        return new UserController(userService());
    }

    private UserService userService() {
        return new UserService();
    }
}
