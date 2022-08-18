package http.response;

import java.util.Map;

public interface HttpResponse {
    String getResponseLine();
    Map<String, String> getHeaders();
    String getBody();
}
