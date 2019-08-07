package webserver.http.dispatcher;

import webserver.http.request.Request;
import webserver.http.response.Response;

abstract class AbstractDispatcher<T extends Request, E extends Response> implements Dispatcher<T, E> {

    protected abstract void doGet(T request, E response);

    protected abstract void doPost(T request, E response);
}
