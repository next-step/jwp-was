package webserver.http.domain.controller;

import webserver.http.domain.request.Request;
import webserver.http.domain.response.Response;

public interface RequestProcessor {

    Response process(Request request);
}
