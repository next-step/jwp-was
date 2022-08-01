package model;

import utils.RequestUtils;

import java.util.HashMap;
import java.util.Map;

import static utils.DelimiterConstants.PATH_PARAMETER_DELIMITER;

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
            return new Path(split[PATH_INDEX], RequestUtils.createRequestDataMap(split[PARAMETER_INDEX]));
        }
        return new Path(split[PATH_INDEX], new HashMap<>());
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

    public boolean equalPath(Path path) {
        return this.path.equals(path.getPath());
    }
}
