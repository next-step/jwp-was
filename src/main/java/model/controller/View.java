package model.controller;

import java.util.Objects;

public class View {
    private static final String REDIRECT_PREFIX = "redirect:";

    private String resourcePath;
    private boolean isRedirect;

    private View(String resourcePath, boolean isRedirect) {
        this.resourcePath = resourcePath;
        this.isRedirect = isRedirect;
    }

    public static View of(String returnValue) {
        String resourcePath = returnValue;
        boolean isRedirect = false;
        if (returnValue.startsWith(REDIRECT_PREFIX)) {
            resourcePath = returnValue.replace(REDIRECT_PREFIX, "");
            isRedirect = true;
        }
        return new View(resourcePath, isRedirect);
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        View view = (View) o;
        return isRedirect == view.isRedirect &&
                Objects.equals(resourcePath, view.resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourcePath, isRedirect);
    }

    @Override
    public String toString() {
        return "View{" +
                "resourcePath='" + resourcePath + '\'' +
                ", isRedirect=" + isRedirect +
                '}';
    }
}
