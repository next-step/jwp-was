package webserver.response;

import webserver.Ordered;
import webserver.request.RequestHolder;

public interface ResponseHandler extends Ordered {

    boolean supports(RequestHolder requestHolder);

    void handle(RequestHolder requestHolder, ResponseHolder responseHolder);

}
