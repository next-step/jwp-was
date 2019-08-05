package webserver.http.mapping;

import webserver.http.dispatcher.*;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resource.ResourceResolver;
import webserver.view.HandlebarViewResolver;
import webserver.view.HtmlViewResolver;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping implements Router<HttpRequest, HttpResponse> {

    private Map<String, Dispatcher> dispatchers;
    private ResourceDispatcher resourceDispatcher;

    public HandlerMapping() {

        dispatchers = new HashMap<>();
        dispatchers.put("/index", new MainDispatcher<>(new HtmlViewResolver()));
        dispatchers.put("/user/form", new RegisterFormDispatcher<>(new HtmlViewResolver()));
        dispatchers.put("/user/login", new LoginDispatcher<>(new HtmlViewResolver()));
        dispatchers.put("/user/list", new UserListDispatcher<>(new HandlebarViewResolver()));
        dispatchers.put("/user/create", new CreateUserDispatcher());

        resourceDispatcher = new ResourceDispatcher(new ResourceResolver());
    }

    @Override
    public void route(HttpRequest request, HttpResponse response) {
        dispatchers.getOrDefault(request.getPath(), resourceDispatcher)
                .service(request, response);
    }

}
