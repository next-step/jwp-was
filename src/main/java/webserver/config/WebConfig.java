package webserver.config;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import webserver.application.UserService;
import webserver.handlers.ControllerContainer;
import webserver.handlers.ControllerContainerImpl;
import webserver.handlers.RequestHandler;
import webserver.handlers.RequestHandlerImpl;
import webserver.handlers.ResponseHandler;
import webserver.handlers.ResponseHandlerImpl;
import webserver.resolvers.DefaultViewResolver;
import webserver.resolvers.JsonViewResolver;
import webserver.resolvers.Resolver;
import webserver.resolvers.ResolverContainer;
import webserver.resolvers.TemplateViewResolver;
import webserver.resolvers.ViewResolverContainer;
import webserver.ui.Controller;
import webserver.ui.UserController;
import webserver.ui.WelcomeController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class WebConfig {
    public static final String NO_SUCH_BEAN_EXCEPTION_MSG = "존재하지 않는 빈입니`다. :";
    private final Map<Class<?>, Object> store = new HashMap<>();

    public WebConfig() {
        addBean(controllerContainer());
        addBean(requestHandler());
        addBean(responseHandler());
        addBean(resolverContainer());
    }

    protected void addBean(Object bean) {
        Class<?>[] beanTypes = bean.getClass().getInterfaces();
        if (beanTypes.length < 1) {
            throw new NoSuchBeanDefinitionException(NO_SUCH_BEAN_EXCEPTION_MSG + bean.getClass().getName());
        }

        store.put(beanTypes[0], bean);
    }

    public <T> T getBean(Class<T> type) {
        if (store.containsKey(type)) {
            return (T) store.get(type);
        }
        throw new NoSuchBeanDefinitionException(NO_SUCH_BEAN_EXCEPTION_MSG + type.getName());
    }

    private <T> T getBeanOrSupply(Class<T> type, Supplier<T> supplier) {
        if (store.containsKey(type)) {
            return getBean(type);
        }

        return supplier.get();
    }

    private RequestHandler requestHandler() {
        ControllerContainer container = getBeanOrSupply(ControllerContainer.class, this::controllerContainer);
        addBean(container);

        return new RequestHandlerImpl(container);
    }

    private ResponseHandler responseHandler() {
        ResolverContainer container = getBeanOrSupply(ResolverContainer.class, this::resolverContainer);
        addBean(container);

        return new ResponseHandlerImpl(container);
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

    private ResolverContainer resolverContainer() {
        return new ViewResolverContainer()
                .add(defaultViewResolver())
                .add(templateViewResolver())
                .add(jsonViewResolver());
    }

    private Resolver defaultViewResolver() {
        return new DefaultViewResolver();
    }

    private Resolver templateViewResolver() {
        return new TemplateViewResolver();
    }

    private Resolver jsonViewResolver() {
        return new JsonViewResolver();
    }

}
