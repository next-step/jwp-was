package webserver;

/**
 * Created by iltaek on 2020/06/14 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ThreadPoolConfiguration {

    private final int corePoolSize;
    private final int maxPoolSize;
    private final int keepAliveTime;
    private final int blockQueueCapacity;

    public ThreadPoolConfiguration(int corePoolSize, int maxPoolSize, int keepAliveTime, int blockQueueCapacity) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.blockQueueCapacity = blockQueueCapacity;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public int getBlockQueueCapacity() {
        return blockQueueCapacity;
    }
}
