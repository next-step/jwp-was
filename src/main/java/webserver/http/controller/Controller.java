package webserver.http.controller;

import webserver.http.domain.HttpRequest;
import webserver.http.domain.HttpResponse;

import java.io.DataOutputStream;

public interface Controller {
    void execute(HttpRequest httpRequest, HttpResponse httpResponse);
}
