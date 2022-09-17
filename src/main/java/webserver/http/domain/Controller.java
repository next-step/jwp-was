package webserver.http.domain;

import java.io.DataOutputStream;

public interface Controller {
    void execute(HttpRequest httpRequest, DataOutputStream dos);
}
