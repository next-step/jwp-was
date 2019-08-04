package webserver.servlet;

import webserver.request.RequestHolder;

public interface Servlet {

    String getName();

    void service(RequestHolder requestHolder);

}
