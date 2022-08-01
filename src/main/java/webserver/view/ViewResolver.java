package webserver.view;

public class ViewResolver {

    private static final String REDIRECT_PREFIX = "redirect:";

    private final String prefix;

    private final String suffix;

    public ViewResolver(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public View resolveView(String view) {
        if (view.startsWith(REDIRECT_PREFIX)) {
            String targetUrl = view.substring(REDIRECT_PREFIX.length());
            return new RedirectView(targetUrl);
        }

        return new HandleBarView(view, prefix, suffix);
    }

}
