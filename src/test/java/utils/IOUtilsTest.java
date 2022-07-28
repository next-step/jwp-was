package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.StringReader;

public class IOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(IOUtilsTest.class);

    @Test
    public void readData() throws Exception {
        String data = "abcd123";
        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        logger.debug("parse body : {}", IOUtils.readData(br, data.length()));
    }

//    @Test
//    void readLines_with_empty_line() throws IOException {
//        List<String> fakeRequestLines = ImmutableList.of(
//                "GET /index.html HTTP/1.1",
//                "Host: localhost:8080",
//                "Connection: keep-alive",
//                "Accept: */*",
//                ""
//        );
//
//        BufferedReader br = new FakeBufferedReader(fakeRequestLines);
//
//        List<String> readLines = IOUtils.readLines(br);
//
//        assertThat(readLines).containsExactly(
//                "GET /index.html HTTP/1.1",
//                "Host: localhost:8080",
//                "Connection: keep-alive",
//                "Accept: */*"
//        );
//    }
//
//    @Test
//    void readLines_with_only_empty_line() throws IOException {
//        List<String> fakeRequestLines = ImmutableList.of(
//                ""
//        );
//
//        BufferedReader br = new FakeBufferedReader(fakeRequestLines);
//
//        List<String> readLines = IOUtils.readLines(br);
//
//        assertThat(readLines).hasSize(0);
//    }
//
//    public static class FakeBufferedReader extends BufferedReader {
//        private String FAKE_EMPTY_READ_LINE = "";
//        private int fakeReadLineIndex = 0;
//        private List<String> fakeReadLines;
//
//        public FakeBufferedReader(List<String> fakeReadLines) {
//            super(new Reader() {
//                @Override
//                public int read(char[] cbuf, int off, int len) {
//                    return 0;
//                }
//
//                @Override
//                public void close() {
//                }
//            });
//            this.fakeReadLines = fakeReadLines;
//        }
//
//        private boolean isOverFakeReadLineIndex() {
//            return fakeReadLineIndex > fakeReadLines.size() - 1;
//        }
//
//        @Override
//        public String readLine() {
//            if (fakeReadLines.isEmpty() || isOverFakeReadLineIndex()) {
//                return FAKE_EMPTY_READ_LINE;
//            }
//
//            return fakeReadLines.get(fakeReadLineIndex++);
//        }
//    }
}
