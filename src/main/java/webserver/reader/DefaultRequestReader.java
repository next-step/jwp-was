package webserver.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DefaultRequestReader implements RequestReader {
    private static final Logger logger = LoggerFactory.getLogger(DefaultRequestReader.class);

    @Override
    public String readStream(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while (!StringUtil.isEmpty(line = bufferedReader.readLine())) {
            stringBuilder.append(line)
                    .append('\n');
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        String rawRequest = stringBuilder.toString();
        printRequest(rawRequest);

        return rawRequest;
    }

    private void printRequest(final String rawRequest) {
        logger.debug("===============================================================RAW REQUEST START");
        logger.debug(rawRequest);
        logger.debug("===============================================================RAW REQUEST END");
    }
}
