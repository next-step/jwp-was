package mvc;

import mvc.controller.Controller;
import was.http.HttpRequest;

public interface RequestMapping {
    Controller getController(HttpRequest request);
}
