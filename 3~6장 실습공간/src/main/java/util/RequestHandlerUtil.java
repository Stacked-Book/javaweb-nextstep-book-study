package util;

import model.User;
import java.util.Map;

public class RequestHandlerUtil {

    private RequestHandlerUtil() {}

    public static String splitRequestMessage(String line) {
        String[] tokens = line.split(" ");
        return tokens[1];
    }

    public static User saveGetUser(String url) {
        if (url.contains("?")) {
            int index = url.indexOf("?");
            url = url.substring(index+1);
            Map<String, String> parsingUrl = HttpRequestUtils.parseQueryString(url);
            return new User(parsingUrl.get("userId"), parsingUrl.get("password"), parsingUrl.get("name"), parsingUrl.get("email"));
        }
        return null;
    }

    public static User savePostUser(String requestBody) {
        Map<String, String> parsingUrl = HttpRequestUtils.parseQueryString(requestBody);
        return new User(parsingUrl.get("userId"), parsingUrl.get("password"), parsingUrl.get("name"), parsingUrl.get("email"));
    }
}
