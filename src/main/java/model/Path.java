package model;

import static utils.DelimiterConstants.QUERY_STRING_DELIMITER;

public class Path {
    private static final int PATH_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;

    private String path;
    private String parameters;

    private Path(String path, String parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static Path of(String path) {
        String[] split = path.split(QUERY_STRING_DELIMITER);
        if (hasParameters(split)) {
            return new Path(split[PATH_INDEX],split[PARAMETER_INDEX]);
        }
        return new Path(split[PATH_INDEX],"");
    }

    private static boolean hasParameters(String[] split) {
        return split.length >= 2;
    }

    public String getPath() {
        return path;
    }

    public String getParameters() {
        return parameters;
    }
}
