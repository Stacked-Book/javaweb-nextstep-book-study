package support.request;

public class RequestBody {
    private final String body;

    public RequestBody() {
        this.body = null;
    }

    public RequestBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    @Override
    public String toString() {
        return "Body{" +
            "body=" + body +
            '}';
    }
}
