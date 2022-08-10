package webserver.http;

public interface Resource {
    boolean match(String url);

    byte[] handle(String url, Headers headers);
}
