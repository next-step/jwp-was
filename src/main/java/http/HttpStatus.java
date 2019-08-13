package http;

public enum HttpStatus {
  OK(200),
  Found(302),
  NOT_FOUND(404),
  Internal_Server_Error(500);


  private int statusCode;

  HttpStatus(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
