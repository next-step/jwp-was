package webserver.http.service;

import webserver.http.request.HttpRequest;

public abstract class GetService implements Service {
    @Override
    public boolean find(HttpRequest httpRequest) {
        return httpRequest.isGet() && pathMatch(httpRequest);
    }

    protected abstract boolean pathMatch(HttpRequest httpRequest);
}
