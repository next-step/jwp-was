package webserver;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import webserver.application.UserService;
import webserver.domain.SessionManager;
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

public class TestWebConfig {
    public static final String NO_SUCH_BEAN_EXCEPTION_MSG = "존재하지 않는 빈입니`다. :";
    private final Map<Class<?>, Object> store = new HashMap<>();

    public TestWebConfig() {

    }

    protected void addBean(Object bean) {
        Class<?>[] beanTypes = bean.getClass().getInterfaces();
        if (beanTypes.length < 1) {
            throw new NoSuchBeanDefinitionException(NO_SUCH_BEAN_EXCEPTION_MSG + bean.getClass().getName());
        }

        store.put(bean.getClass(), bean);
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

    public RequestHandler requestHandler() {
        ControllerContainer container = getBeanOrSupply(ControllerContainer.class, this::controllerContainer);
        addBean(container);

        return new RequestHandlerImpl(container);
    }

    public ResponseHandler responseHandler() {
        ResolverContainer container = getBeanOrSupply(ResolverContainer.class, this::resolverContainer);
        addBean(container);

        return new ResponseHandlerImpl(container);
    }


    public ControllerContainer controllerContainer() {
        ControllerContainerImpl controllerContainer = ControllerContainerImpl.newInstance();

        return controllerContainer.addController(welcomeController())
                .addController(userController());
    }

    WelcomeController welcomeController() {
        return new WelcomeController();
    }

    public UserController userController() {
        return new UserController(userService(), sessionManager());
    }

    public SessionManager sessionManager() {
        return new SessionManager();
    }

    public UserService userService() {
        return new UserService();
    }

    public ResolverContainer resolverContainer() {
        return new ViewResolverContainer()
                .add(defaultViewResolver())
                .add(templateViewResolver())
                .add(jsonViewResolver());
    }

    public Resolver defaultViewResolver() {
        return new DefaultViewResolver();
    }

    public Resolver templateViewResolver() {
        return new TemplateViewResolver();
    }

    public Resolver jsonViewResolver() {
        return new JsonViewResolver();
    }

}
