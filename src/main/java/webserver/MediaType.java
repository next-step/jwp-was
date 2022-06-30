package webserver;

public class MediaType {

  public static final MediaType TEXT_HTML_UTF8;

  static {
    TEXT_HTML_UTF8 = new MediaType("text/html;charset=utf-8");
  }

  private final String text;

  MediaType(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}

