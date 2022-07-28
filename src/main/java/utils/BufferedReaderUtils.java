package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferedReaderUtils {
    public static List<String> lines(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();

        String line = br.readLine();
        lines.add(line);

        while (line != null) {
            line = br.readLine();
            lines.add(line);
        }

        return lines;
    }
}
