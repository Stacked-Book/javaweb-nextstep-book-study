package response;

import java.util.UUID;

public class SessionHandler {

    private HttpResponseImpl response;

    public void setSessionID() {
        response.addHeader("Set-Cookie", "JSESSIONID="
                + UUID.randomUUID());
    }
}
