package http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueryString {

  private static final String AMPERSAND_DELIMITER = "&";
  private static final String EQUALS_DELIMITER = "=";
  private static final String DEFAULTS_VALUE = "";
  private static final int KEY_INDEX = 0;
  private static final int VALUE_INDEX = 1;
  private static final int HAS_VALUE_CONDITION_LENGTH = 1;

  private Map<String, String> params;

  public QueryString(Map<String, String> params) {
    this.params = params;
  }

  public static QueryString parse(String queryString) {
    Map<String, String> params = new HashMap<>();

    for (String param : queryString.split(AMPERSAND_DELIMITER)) {
      String[] keyAndValue = param.split(EQUALS_DELIMITER);
      params.put(keyAndValue[KEY_INDEX],
          keyAndValue.length > HAS_VALUE_CONDITION_LENGTH ? keyAndValue[VALUE_INDEX]
              : DEFAULTS_VALUE);
    }
    return new QueryString(params);
  }

  public String getParam(String key) {
    return params.get(key);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryString that = (QueryString) o;
    return Objects.equals(params, that.params);
  }

  @Override
  public int hashCode() {
    return Objects.hash(params);
  }
}
