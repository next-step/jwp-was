package webserver.mapper;

import enums.HttpMethod;
import webserver.handler.ModelView;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.resolvers.view.ViewResolver;

public class ViewActionRequestMapper implements RequestMapper {

    private final ActionRequestMapper actionRequestMapper;
    
    private final ViewResolver viewResolver;


    public ViewActionRequestMapper(ViewResolver viewResolver, ActionRequestMapper actionRequestMapper) {
        this.viewResolver = viewResolver;
        this.actionRequestMapper = actionRequestMapper;
    }

    @Override
    public boolean isMatchedRequest(HttpMethod method, String requestUri) {
        return this.actionRequestMapper.isMatchedRequest(method, requestUri);
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.actionRequestMapper.handle(httpRequest, httpResponse);
        this.viewResolver.resolve(httpRequest, httpResponse);
    }
}
