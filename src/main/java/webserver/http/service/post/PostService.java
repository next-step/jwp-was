package webserver.http.service.post;

import webserver.http.request.HttpRequest;
import webserver.http.service.Service;

import java.util.List;

public abstract class PostService implements Service {
    public static List<Service> services = List.of(new UserCreatePostService(), new LoginService());

    @Override
    public boolean find(HttpRequest httpRequest) {
        return httpRequest.isPost() && pathMatch(httpRequest);
    }

    protected abstract boolean pathMatch(HttpRequest httpRequest);
}
