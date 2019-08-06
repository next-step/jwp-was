package webserver;

import session.InMemorySessionManager;
import session.SessionManager;
import session.UuidRandomGenerator;

/**
 * Created by youngjae.havi on 2019-08-06
 */
public class ServletContextImpl {

    public static SessionManager getSessionManager() {
        return new InMemorySessionManager(new UuidRandomGenerator());
    }

}
