package webserver.servlet;

import java.util.List;

public class ServletConfig {

    private ServletConfig() {
    }

    public static List<Servlet> servlets() {
        return List.of(
                new SignUpServlet(),
                new SignInServlet(),
                new UserListServlet()
        );
    }
}
