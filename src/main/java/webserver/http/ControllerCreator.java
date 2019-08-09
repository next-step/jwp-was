package webserver.http;

/**
 * 웹 기본 컨트롤러 구조
 */
public interface ControllerCreator {
    HttpResponse doMethodCall(HttpRequest httpRequest);
    HttpResponse doPost(HttpRequest httpRequest);
    HttpResponse doGet(HttpRequest httpRequest);
}
