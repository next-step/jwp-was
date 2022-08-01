package webserver.http.response;


public class ResponseBody {

    private final View view;

    public ResponseBody(View view) {
        this.view = view;
    }

    public static ResponseBody of200(String viewPath) {
        return new ResponseBody(new View(viewPath));
    }

    public View getView() {
        return view;
    }
}
