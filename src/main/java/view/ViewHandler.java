package view;

import org.springframework.util.StringUtils;

public class ViewHandler {
    private String redirectUrl;
    private String returnUrl;
    private String cookie;

    public void redirect(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void returnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public void addCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getCookie() {
        return cookie;
    }

    public boolean isExitRedirectUrl() {
        return StringUtils.hasText(redirectUrl);
    }
}
