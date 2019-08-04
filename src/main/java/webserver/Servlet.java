package webserver;

public interface Servlet {

    boolean isMapping(Request request);

    Response supply(Request request) throws Exception;
}
