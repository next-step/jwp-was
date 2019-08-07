package actions;

import webserver.http.request.HttpMethod;

public interface ActionCreator {
    Action creater(HttpMethod method, String path);
}
