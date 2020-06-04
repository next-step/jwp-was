package webserver.reader;

import utils.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultRequestReader implements RequestReader {

    @Override
    public String readStream(final InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while (!StringUtil.isEmpty(line = bufferedReader.readLine())) {
            stringBuilder.append(line)
                    .append('\n');
        }

        String rawRequest = stringBuilder.toString();
        printRequest(rawRequest);

        return stringBuilder.toString();
    }

    private void printRequest(final String rawRequest) {
        System.out.println("===============================================================RAW REQUEST START");
        System.out.print(rawRequest);
        System.out.println("===============================================================RAW REQUEST END");
    }
}
