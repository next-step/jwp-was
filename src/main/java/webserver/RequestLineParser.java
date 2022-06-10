package webserver;

public class RequestLineParser {

    public RequestLine parse(String input) {
        String SPACE_DELIMITER = " ";

        String[] splitInput = input.split(SPACE_DELIMITER);

        return new RequestLine(HttpMethod.valueOf(splitInput[0]),
                Uri.from(splitInput[1]),
                Protocol.from(splitInput[2]));
    }
}
