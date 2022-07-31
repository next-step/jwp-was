package model;

import java.util.HashMap;
import java.util.Map;

import static utils.DelimiterConstants.*;

public class Path {
    private static final int PATH_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;

    private String path;
    private Map<String, String> parameters;

    private Path(String path, Map<String, String> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static Path of(String path) {
        String[] split = path.split(PATH_PARAMETER_DELIMITER);
        if (hasParameters(split)) {
            return new Path(split[PATH_INDEX], createParameters(split[PARAMETER_INDEX]));
        }
        return new Path(split[PATH_INDEX], new HashMap<>());
    }

    private static Map<String, String> createParameters(String stringParameters) {
        Map<String, String> map = new HashMap<>();
        String[] splitParameters = stringParameters.split(QUERY_STRING_DELIMITER);
        for (String parameter : splitParameters) {
            String[] splitParameter = parameter.split(PARAMETER_KEY_VALUE_DELIMITER);
            map.put(splitParameter[0], splitParameter[1]);
        }
        return map;
    }

    private static boolean hasParameters(String[] split) {
        return split.length >= 2;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
