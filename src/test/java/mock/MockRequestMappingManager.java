package mock;

import webserver.RequestMappingManager;

import java.util.HashMap;
import java.util.Map;

public class MockRequestMappingManager extends RequestMappingManager {
    private final Map<String, String> requestMap = new HashMap<>();

    public MockRequestMappingManager() {
        requestMap.put("/index.html", "");
    }
}
