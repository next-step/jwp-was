package http.controller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import http.HttpSessionManager;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public abstract class AbstractController implements Controller{

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if (isGET(request)) {
            doGET(request, response);
        } else {
            doPOST(request, response);
        }
    }

    protected HttpSession getCurrentSession(HttpRequest request) {
        String sessionId = request.getCookie(HttpSessionManager.SESSION_NAME);
        HttpSession session = request.getSessionManager().getSession(sessionId);
        if (session == null) {
            session = request.getSessionManager().createSession(sessionId);
        }
        return session;
    }

    private boolean isGET(HttpRequest request) {
        return request.getMethod() == HttpMethod.GET;
    }

    protected void doPOST(HttpRequest request, HttpResponse response){}

    protected void doGET(HttpRequest request, HttpResponse response){}
}
