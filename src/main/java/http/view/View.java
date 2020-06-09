package http.view;

import http.requests.HttpRequest;
import http.responses.HttpResponse;

/**
 * 렌더링의 책임을 갖는다.
 */
public interface View {
    void render(HttpRequest request, HttpResponse response) throws Exception;
}
