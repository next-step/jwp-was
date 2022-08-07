package webserver.http.controller;

import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;
import webserver.http.domain.session.Session;
import webserver.http.domain.session.SessionContextHolder;
import webserver.http.domain.session.SessionStorage;

public class SessionCompositeRequestProcessor implements RequestProcessor {
    private final RequestProcessor requestProcessor;
    private final SessionStorage sessionStorage;

    public SessionCompositeRequestProcessor(RequestProcessor requestProcessor, SessionStorage sessionStorage) {
        this.requestProcessor = requestProcessor;
        this.sessionStorage = sessionStorage;
    }

    public Response process(Request request) {
        try {
            Session session = sessionStorage.getOrGenerateSession(request);
            SessionContextHolder.saveCurrentSession(session);
            Response response = requestProcessor.process(request);
            sessionStorage.teardownAfterProcess(response);
            return response;
        } catch (RuntimeException e) {
            throw e;
        } finally {
            SessionContextHolder.removeCurrentSession();
        }
    }
}
