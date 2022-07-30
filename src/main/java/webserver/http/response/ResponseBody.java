package webserver.http.response;

public class ResponseBody {

    private final View view;

    public ResponseBody(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
