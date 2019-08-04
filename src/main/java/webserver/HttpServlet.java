package webserver;

public interface HttpServlet {

    boolean isMapping(Request request);

    Response service(Request request) throws Exception;
}
