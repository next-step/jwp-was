package webserver.service;

import model.User;
import webserver.http.Parameters;
import webserver.http.RequestLine;

public class UserService implements WebService {
    @Override
    public void process(Object... obj) {
        RequestLine requestLine = (RequestLine)obj[0];
        Parameters parameters = requestLine.getPath().getParameters();
        User user = User.newInstance(parameters);
    }
}
