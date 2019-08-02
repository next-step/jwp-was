package http;

import static java.util.Collections.EMPTY_MAP;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Parameters {

  private static final String AMPERSAND_DELIMITER = "&";
  private static final String EQUALS_DELIMITER = "=";
  private static final String DEFAULTS_VALUE = "";
  private static final int KEY_INDEX = 0;
  private static final int VALUE_INDEX = 1;
  private static final int HAS_VALUE_CONDITION_LENGTH = 1;

  public static final Parameters EMPTY = new Parameters(EMPTY_MAP);
  private Map<String, String> parameters;

  private Parameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }

  public static Parameters parse(String queryString) {
    Map<String, String> params = new HashMap<>();

    for (String param : queryString.split(AMPERSAND_DELIMITER)) {
      fillParams(params, param);
    }

    return new Parameters(params);
  }

  private static void fillParams(Map<String, String> params, String param) {
    String[] keyAndValue = param.split(EQUALS_DELIMITER);
    params.put(keyAndValue[KEY_INDEX],
        keyAndValue.length > HAS_VALUE_CONDITION_LENGTH ? keyAndValue[VALUE_INDEX]
            : DEFAULTS_VALUE);
  }

  public String getParameter(String key) {
    return parameters.get(key);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Parameters that = (Parameters) o;
    return Objects.equals(parameters, that.parameters);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameters);
  }
}
