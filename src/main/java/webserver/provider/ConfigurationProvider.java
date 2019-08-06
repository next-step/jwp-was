package webserver.provider;

public class ConfigurationProvider {

    public static String LOGINED_KEY = "logined";

    public static String welcomePage() {
        return "/index.html";
    }

    public static String errorPage() {
        return "/error.html";
    }

}
