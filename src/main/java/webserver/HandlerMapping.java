package webserver;

import user.controller.Controller;
import user.controller.ControllerEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HandlerMapping {
    private Map<String, Controller> controllerMap;

    public HandlerMapping() {
        controllerMap = new HashMap<>();
        initialize();
    }

    private void initialize() {
        controllerMap = Arrays.stream(ControllerEnum.values()).collect(Collectors.toUnmodifiableMap(ControllerEnum::getPath,
                        ControllerEnum -> (Controller) ControllerEnum.getClassObject(), (p1, p2) -> p1));
    }

    public HandlerMapping(Map<String, Controller> controllerMap) {
        this.controllerMap = controllerMap;
    }

    public Map<String, Controller> getControllerMap() {
        return Collections.unmodifiableMap(controllerMap);
    }
}
