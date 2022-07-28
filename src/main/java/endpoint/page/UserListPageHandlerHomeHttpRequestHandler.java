package endpoint.page;

import application.GetUsersService;
import endpoint.Endpoint;
import endpoint.HttpRequestEndpointHandler;
import endpoint.InterceptableHandler;
import model.User;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.response.HttpResponseMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@InterceptableHandler(interceptor = PreLoginValidationFromCookieProxyHandler.class)
public class UserListPageHandlerHomeHttpRequestHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT = "/user/list.html";

    public UserListPageHandlerHomeHttpRequestHandler() {
        super(new Endpoint(HttpMethod.GET, ENDPOINT));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {
        List<User> users = GetUsersService.getAllUsers();

        Map<String, Object> viewModelMap = new HashMap<>();
        viewModelMap.put("users", users);

        return HttpResponseMessage.dynamicPage(ENDPOINT, viewModelMap);
    }
}
