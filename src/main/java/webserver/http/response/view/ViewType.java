package webserver.http.response.view;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public enum ViewType {

    RESOURCE("resource::", StaticResourceViewRenderer::new),
    REDIRECT("redirect::", RedirectViewRenderer::new),
    TEMPLATE("template::", TemplateResourceViewRenderer::new),
    ;

    private String prefix;
    private final ViewCreator creator;

    ViewType(String prefix, ViewCreator creator) {
        this.prefix = prefix;
        this.creator = creator;
    }

    public String getPrefix() {
        return prefix;
    }

    public ViewRenderer getViewRenderer() {
        return creator.create(this.prefix);
    }

    @FunctionalInterface
    public interface ViewCreator {
        ViewRenderer create(String prefix);
    }
}
