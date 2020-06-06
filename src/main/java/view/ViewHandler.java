package view;

import static view.ReturnType.*;

public class ViewHandler {
    private ReturnType type;
    private String view;
    private String cookie;

    public ReturnType getType() {
        return type;
    }

    public String getView() {
        return view;
    }

    public String getCookie() {
        return cookie;
    }

    public void addCookie(String cookie) {
        this.cookie = cookie;
    }

    public void redirect(String url) {
        this.type = REDIRECT;
        this.view = url;
    }


    public void returnFile(String file) {
        this.type = FILE;
        this.view = file;
    }

    public void addTemplate(String template) {
        this.type = TEMPLATE;
        this.view = template;
    }

    public boolean isFile() {
        return type == FILE;
    }

    public boolean isTemplate() {
        return type == TEMPLATE;
    }
}
