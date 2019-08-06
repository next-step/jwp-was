package servlet;

public enum ServletMappingInfo {
  USER_CREATE("/user/create", UserCreateServlet::new),
  USER_LOGIN("/user/login", LoginServlet::new),
  USER_LIST("/user/list", UserListServlet::new);

  private String path;
  private ServletCreator creator;

  ServletMappingInfo(String path, ServletCreator creator) {
    this.path = path;
    this.creator = creator;
  }

  public String getPath() {
    return path;
  }

  public ServletCreator getCreator() {
    return creator;
  }
}
