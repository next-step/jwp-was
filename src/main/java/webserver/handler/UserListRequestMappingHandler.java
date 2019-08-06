package webserver.handler;

import db.DataBase;
import webserver.http.CustomCookie;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.ModelAndView;
import webserver.resolver.ViewResolver;

import java.util.HashMap;
import java.util.Map;

public class UserListRequestMappingHandler extends RequestMappingHandler {

    public UserListRequestMappingHandler(ViewResolver viewResolver) {
        super(viewResolver);
    }

    @Override
    protected HttpResponse doGet(HttpRequest httpRequest) throws Exception {
        Boolean logined = Boolean.valueOf(httpRequest.getCookie(CustomCookie.LOGINED));
        if (!logined) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", viewResolver.getContentType());
            headers.put("Location", "/user/login.html");
            return HttpResponse.redirect(headers, viewResolver.resolve("/user/login.html"));
        }

        ModelAndView modelAndView = new ModelAndView("user/list", DataBase.findAll());
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", viewResolver.getContentType());
        return HttpResponse.ok(headers, viewResolver.resolve(modelAndView));
    }

    @Override
    protected String getRequestMapping() {
        return "/user/list";
    }
}
