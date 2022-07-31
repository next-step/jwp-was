package webserver.http.domain.controller;

import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

interface Controller {
    boolean requires(Request request);
    Response handle(Request request);
}
