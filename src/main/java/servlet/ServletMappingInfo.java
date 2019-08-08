package servlet;

public enum ServletMappingInfo {
  USER_CREATE("/user/create", new UserCreateServlet()),
  USER_LOGIN("/user/login", new LoginServlet()),
  USER_LIST("/user/list", new UserListServlet());

  private String path;
  private HttpServlet creator;

  ServletMappingInfo(String path, HttpServlet creator) {
    this.path = path;
    this.creator = creator;
  }

  public String getPath() {
    return path;
  }

  public HttpServlet getCreator() {
    return creator;
  }
}
