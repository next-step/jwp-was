package http;

public enum HttpMethod {

  POST, GET;

  public boolean isPost() {
    return this == POST;
  }

  public boolean isGet() {
    return this == GET;
  }
}
