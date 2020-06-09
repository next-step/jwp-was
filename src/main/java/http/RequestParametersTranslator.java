package http;

import java.util.Map;

public interface RequestParametersTranslator {
    Map<String, String> create();
}
