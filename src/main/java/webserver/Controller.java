package webserver;

@FunctionalInterface
public interface Controller {

    Response service(Request request) throws Exception;
}
