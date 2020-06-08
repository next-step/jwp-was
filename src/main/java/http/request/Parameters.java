package http.request;

import java.util.HashMap;
import java.util.Map;

public class Parameters {
    private Map<String, String> parameters;

    private Parameters() {
        this.parameters = new HashMap<>();
    }

    public static Parameters newInstance() {
        return new Parameters();
    }

    public void parse(final String query) {

    }
}
