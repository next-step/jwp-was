package webserver.http.service;

import webserver.http.request.HttpRequest;

public abstract class PostService implements Service {
    @Override
    public boolean find(HttpRequest httpRequest) {
        return httpRequest.isPost() && pathMatch(httpRequest);
    }

    protected abstract boolean pathMatch(HttpRequest httpRequest);
}
