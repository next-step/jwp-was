package mvc;

import mvc.controller.Controller;
import webserver.http.HttpRequest;

public interface RequestMapping {
    Controller getController(HttpRequest request);
}
