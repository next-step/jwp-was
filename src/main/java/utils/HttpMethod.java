package utils;

import java.util.Arrays;

public enum HttpMethod {
  GET, POST;

  public static boolean matchedPropertyOf(String requestMethod) {
    return Arrays.stream(values())
        .anyMatch(method -> requestMethod.equals(method.name()));
  }
}
