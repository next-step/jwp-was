package view;

public interface View {

    String SLASH = "/";
    String DEFAULT_FILE_PATH = "index.html";

    byte[] responseBody();
    int contentLength();
}
