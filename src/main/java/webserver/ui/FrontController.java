package webserver.ui;

public class FrontController {
    public static FrontController newInstance() {
        return new FrontController();
    }

    public boolean support(String path){
        return "/index.html".equals(path);
    }

    public FrontController addController(Controller controller) {
        return this;
    }
}
