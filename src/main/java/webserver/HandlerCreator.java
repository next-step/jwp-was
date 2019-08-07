package webserver;

import webserver.template.ViewResolver;

public interface HandlerCreator {
    Handler createHandler(ViewResolver viewResolver);
}
