package webserver;

public class Param {
    private static final String INVALID_PARAMETERS = "잘못된 파라미터";
    private static final int PARAM_SIZE = 2;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private String key;
    private String value;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static Param of(String params, String regex) {
        if (params == null || params.isEmpty() || regex == null || regex.isEmpty()) {
            throw new IllegalArgumentException(INVALID_PARAMETERS);
        }
        String[] tokens = params.split(regex);
        if (tokens.length != PARAM_SIZE) {
            return null;
        }

        return new Param(tokens[KEY_INDEX], tokens[VALUE_INDEX]);
    }
}
