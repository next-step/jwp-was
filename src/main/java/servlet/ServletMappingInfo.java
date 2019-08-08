package servlet;

public enum ServletMappingInfo {
  USER_CREATE("/user/create", new UserCreateServlet()),
  USER_LOGIN("/user/login", new LoginServlet()),
  USER_LIST("/user/list", new UserListServlet());

  private String path;
  private HttpServlet servlet;

  ServletMappingInfo(String path, HttpServlet servlet) {
    this.path = path;
    this.servlet = servlet;
  }

  public String getPath() {
    return path;
  }

  public HttpServlet getServlet() {
    return servlet;
  }
}
