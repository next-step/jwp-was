package slipp;

import slipp.config.SlippConfig;
import webserver.WebApplicationServer;

public class SlippApplication {
    public static void main(String[] args) {
        WebApplicationServer webApplicationServer = SlippConfig.webApplicationServer();
        webApplicationServer.run(args);
    }
}
