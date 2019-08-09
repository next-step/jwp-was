package webserver.session;

import jdk.nashorn.internal.ir.annotations.Ignore;
import model.User;
import webserver.HttpSession;

@Ignore
public class MockHttpSession implements HttpSession {

    @Override
    public String getId() {
        return "55717547-6aa9-45a2-8288-d02b47334413";
    }

    @Override
    public void setAttribute(String name, Object value) {
    }

    @Override
    public Object getAttribute(String name) {
        return new User("javajigi", "password", "email@gmail.com");
    }

    @Override
    public void removeAttribute(String name) {

    }

    @Override
    public void invalidate() {

    }
}