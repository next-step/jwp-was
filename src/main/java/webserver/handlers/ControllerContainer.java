package webserver.handlers;

import webserver.domain.RequestLine;
import webserver.ui.Controller;

public interface ControllerContainer {

    boolean support(RequestLine requestLine);

    Controller findController(RequestLine requestLine);

    ControllerContainer addController(Controller controller);
}
