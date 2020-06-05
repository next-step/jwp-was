package http.responsetemplate;

import http.HttpResponse;

import java.io.DataOutputStream;

public interface ResponseTemplate {
    void write(HttpResponse httpResponse, DataOutputStream dataOutputStream);
}
