package http.response;

import utils.StringUtil;

public class Forward {
    private String forward;

    private Forward() {
        this.forward = "";
    }

    public static Forward newInstance() {
        return new Forward();
    }

    public void setForward(final String forward) {
        validate(forward);
        this.forward = forward;
    }

    private void validate(String forward) {
        if (StringUtil.isEmpty(forward)) {
            throw new IllegalArgumentException("Forward can't be a null or empty string");
        }
    }

    public boolean isForward() {
        return !StringUtil.isEmpty(forward);
    }

    public String getForward() {
        return forward;
    }
}
