package webserver.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;
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
        int contentLength = 0;
        String line;

        while (!StringUtil.isEmpty(line = bufferedReader.readLine())) {
            if (line.startsWith("Content-Length")) {
                contentLength = Integer.parseInt(line.split(" ")[1]);
            }

            stringBuilder.append(line).append('\n');
        }

        if (contentLength > 0) {
            stringBuilder.append('\n')
                    .append(IOUtils.readData(bufferedReader, contentLength));
        }

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
