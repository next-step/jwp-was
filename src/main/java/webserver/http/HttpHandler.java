package webserver.http;

public interface HttpHandler extends Ordered {

    boolean supports(HttpRequest httpRequest);

    void handle(HttpRequest httpRequest, HttpResponse httpResponse);

}
