package webserver.http;

/**
 * 웹 기본 컨트롤러 구조
 */
public interface ControllerCreator {
    void doMethodCall(HttpRequest httpRequest);
    void doPost(HttpRequest httpRequest);
    void doGet(HttpRequest httpRequest);
}
