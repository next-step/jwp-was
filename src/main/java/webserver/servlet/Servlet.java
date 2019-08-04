package webserver.servlet;

import webserver.response.ResponseHolder;
import webserver.request.RequestHolder;

public interface Servlet {

    String getName();

    void service(RequestHolder requestHolder, ResponseHolder responseHolder);

}
