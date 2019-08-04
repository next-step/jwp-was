package webserver.resolvers.view;

import webserver.handler.ModelView;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface ViewResolver {
    void resove(ModelView modelView, HttpRequest httpRequest, HttpResponse httpResponse) ;
}
