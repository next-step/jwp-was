package webserver.http.service;

import webserver.http.request.HttpRequest;

import java.util.List;

public abstract class GetService implements Service {

    public static List<Service> services = List.of(new ViewService(), new UserCreateGetService(), new UserListService());

    @Override
    public boolean find(HttpRequest httpRequest) {
        return httpRequest.isGet() && pathMatch(httpRequest);
    }

    protected abstract boolean pathMatch(HttpRequest httpRequest);
}
