package webserver;

import webserver.view.ViewResolver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class HandlerMappings {

    private static Map<String, Function<ViewResolver, byte[]>> mappings;

    static {
        mappings = new HashMap<>();
    }

    public static void initialize() {
        mappings.put("/index.html", viewResolver -> {
            try {
                viewResolver.render("/index.html");
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            return new byte[0];
        });
    }

    public static Function<ViewResolver, byte[]> getMapping(String key) {
        return mappings.get(key);
    }
}
