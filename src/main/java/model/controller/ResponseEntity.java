package model.controller;

import java.util.Objects;

public class ResponseEntity {
    private static final String REDIRECT_PREFIX = "redirect:";

    private String resourcePath;
    private boolean isRedirect;
    private boolean hasLoginCookie;

    private ResponseEntity(String resourcePath, boolean isRedirect) {
        this(resourcePath, isRedirect, false);
    }

    private ResponseEntity(String resourcePath, boolean isRedirect, boolean hasLoginCookie) {
        this.resourcePath = resourcePath;
        this.isRedirect = isRedirect;
        this.hasLoginCookie = hasLoginCookie;
    }

    public static ResponseEntity of(String returnValue) {
        String resourcePath = returnValue;
        boolean isRedirect = false;
        if (returnValue.startsWith(REDIRECT_PREFIX)) {
            resourcePath = returnValue.replace(REDIRECT_PREFIX, "");
            isRedirect = true;
        }
        return new ResponseEntity(resourcePath, isRedirect);
    }

    public static ResponseEntity of(String returnValue, boolean hasLoginCookie) {
        ResponseEntity newView = of(returnValue);
        newView.hasLoginCookie = hasLoginCookie;
        return newView;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public boolean isHasLoginCookie() {
        return hasLoginCookie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseEntity responseEntity = (ResponseEntity) o;
        return isRedirect == responseEntity.isRedirect &&
                Objects.equals(resourcePath, responseEntity.resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourcePath, isRedirect);
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "resourcePath='" + resourcePath + '\'' +
                ", isRedirect=" + isRedirect +
                '}';
    }
}
