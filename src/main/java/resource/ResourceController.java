package resource;

import controller.BaseController;
import http.request.HttpRequest;
import http.response.HttpResponse;

public class ResourceController extends BaseController {

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        super.service(httpRequest, httpResponse);
    }

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.forward(httpRequest.getPath());
    }
}
