package webserver;

import handler.Handler;
import handler.HandlerMatcher;
import handler.HandlerRegister;
import handler.StaticResourceHandler;
import handler.UserHandler;
import http.Method;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.view.RedirectView;

public class RequestHandlerMapping {

    private static final HandlerRegister handlerRegister = new HandlerRegister();

    static {
        register();
    }

    private static void register() {
        UserHandler userHandler = new UserHandler();

        handlerRegister.add(
            new HandlerMatcher(Method.POST, "\\/user\\/create"),
            httpRequest -> userHandler.create(httpRequest)
        );

        handlerRegister.add(
            new HandlerMatcher(Method.POST, "\\/user\\/login"),
            httpRequest -> userHandler.login(httpRequest)
        );

        handlerRegister.add(
            new HandlerMatcher(Method.GET, "\\/user\\/list\\.html"),
            httpRequest -> userHandler.list(httpRequest)
        );

        handlerRegister.add(
            new HandlerMatcher(Method.GET, "\\/(css|fonts|images|js)\\/.+"),
            new StaticResourceHandler("static")
        );

        handlerRegister.add(
            new HandlerMatcher(Method.GET, "\\/$"),
            httpRequest -> new HttpResponse(new RedirectView("/index.html"))
        );

        handlerRegister.add(
            new HandlerMatcher(Method.GET, ".*"),
            new StaticResourceHandler("templates")
        );
    }

    public HttpResponse handle(HttpRequest httpRequest) {
        Handler handler = handlerRegister.find(httpRequest.getMethod(), httpRequest.getPath());
        return handler.handle(httpRequest);
    }
}
