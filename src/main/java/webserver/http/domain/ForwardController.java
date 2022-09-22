package webserver.http.domain;

public class ForwardController implements Controller {

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward("./templates" + httpRequest.path());
    }
}
