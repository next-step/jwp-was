package utils;

import java.net.URLConnection;
import java.util.Optional;

public class MimeTypeUtils {

    public static String guessContentTypeFromName(String fileName, String accept){

        return Optional.ofNullable(URLConnection.guessContentTypeFromName(fileName))
                .orElseGet(() -> {

                    if(fileName.matches(".*?\\.js")) {
                        return "application/javascript;";
                    }

                    if(fileName.matches(".*?\\.css")) {
                        return "text/css;";
                    }

                    return accept;
                });

    }

}

