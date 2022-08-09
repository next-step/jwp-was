package webserver;

public class WebApplicationConfig {
    private Integer threadPoolSize;

    public WebApplicationConfig(Integer threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public Integer getThreadPoolSize() {
        return threadPoolSize;
    }
}
