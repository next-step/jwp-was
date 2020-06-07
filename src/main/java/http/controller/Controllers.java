package http.controller;

import java.util.HashMap;
import java.util.Map;

public class Controllers {
    private Map<String, Controller> controllerMap = new HashMap<>();

    public void addController(Controller controller) {
        this.controllerMap.put(controller.getRequestMappingValue(), controller);
    }

    public Controller get(String key) {
        return controllerMap.get(key);
    }

    public boolean containsKey(String key) {
        return controllerMap.containsKey(key);
    }
}
