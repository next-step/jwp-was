package http;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private String name;

    HttpMethod(String name) {
        this.name = name;
    }

    public static HttpMethod of(String name) {
        if (name.equals(HttpMethod.GET.getName()))
            return HttpMethod.GET;
        if (name.equals(HttpMethod.POST.getName()))
            return HttpMethod.POST;
        return null;
    }

    public String getName() {
        return name;
    }
}
