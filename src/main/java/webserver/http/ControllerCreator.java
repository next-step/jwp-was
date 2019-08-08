package webserver.http;

import webserver.domain.HttpResponseEntity;

/**
 * 웹 기본 컨트롤러 구조
 */
public interface ControllerCreator {
    HttpResponseEntity doMethodCall(HttpRequest httpRequest);
    HttpResponseEntity doPost(HttpRequest httpRequest);
    HttpResponseEntity doGet(HttpRequest httpRequest);
}
