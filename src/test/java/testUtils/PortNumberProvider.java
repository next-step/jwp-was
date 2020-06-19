package testUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicInteger;

public class PortNumberProvider {

    private static final AtomicInteger port = new AtomicInteger(8080);

    public static int fetchPortNumber() {
        final int curPort = port.addAndGet(1);
        if (isAlreadyUsing(curPort)) {
            return fetchPortNumber();
        }
        return curPort;
    }

    private static boolean isAlreadyUsing(int port) {
        ServerSocket socket;

        try {
            socket = new ServerSocket(port);
            socket.close();
        } catch (BindException be) {
            return true;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return false;
    }

    public static String fetchPortNumberStr() {
        return String.valueOf(fetchPortNumber());
    }
}
