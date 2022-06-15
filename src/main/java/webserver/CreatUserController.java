package webserver;

public class CreatUserController implements Controller {

    public Response serving(Request request) {
        return new Response("user/form.html");
    }
}
