package http;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parameters extends LinkedMultiValueMap<String, String> {

    public static final String PARAMETER_DELIMITER = "&";
    public static final String MULTIPLE_PARAMETER_DELIMITER = ",";
    public static final String NAME_VALUE_DELIMITER = "=";

    private Parameters(MultiValueMap<String, String> parameters) {
        super(parameters);
    }

    public static Parameters from(String queryString) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Arrays.stream(queryString.split(PARAMETER_DELIMITER))
                .map(parameter -> parameter.split(NAME_VALUE_DELIMITER))
                .forEach(nameValue -> parameters.put(nameValue[0], nameValue.length == 2 ? Arrays.asList(nameValue[1].split(MULTIPLE_PARAMETER_DELIMITER)) : new ArrayList<>()));

        return new Parameters(parameters);
    }

    public static Parameters emptyParameters() {
        return new Parameters(new LinkedMultiValueMap<>());
    }

    public String getParameter(String name) {
        List<String> values = get(name);

        return values == null ? null : String.join(MULTIPLE_PARAMETER_DELIMITER, values.toArray(new String[] {}));
    }

    public String[] getParameterValues(String name) {
        return get(name).toArray(new String[]{});
    }
}
