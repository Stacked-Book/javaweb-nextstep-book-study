package util;

public class RequestHandlerUtil {
    public static String parsing(String line) {
        String[] tokens = line.split(" ");
        return tokens[1];
    }
}
