package webserver.controller;

import http.request.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import webserver.exceptions.MethodNotAllowedException;
import webserver.session.HttpSession;
import webserver.session.SessionStore;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) {

        HttpMethod httpMethod = httpRequest.getMethod();
        if (isGet(httpMethod)) {
            doGet(httpRequest, httpResponse);
        } else if (isPost(httpMethod)) {
            doPost(httpRequest, httpResponse);
        }

        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            String id = session.getId();
            SessionStore.add(id, session);
            httpResponse.addCookie("JSESSIONID", id);
        }
    }

    protected boolean isGet(HttpMethod httpMethod) {
        return httpMethod == HttpMethod.GET;
    }

    protected boolean isPost(HttpMethod httpMethod) {
        return httpMethod == HttpMethod.POST;
    }

    protected void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException(HttpMethod.GET);
    };

    protected void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowedException(HttpMethod.POST);
    }

}
