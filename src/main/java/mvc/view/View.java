package mvc.view;

import was.http.HttpRequest;
import was.http.HttpResponse;

public interface View {
    void render(HttpRequest request, HttpResponse response) throws Exception;
}
