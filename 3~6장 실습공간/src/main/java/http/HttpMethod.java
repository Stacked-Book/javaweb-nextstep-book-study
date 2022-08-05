package http;

public enum HttpMethod {
    GET,
    POST;

    public boolean isPost() {
        return this == POST;
    }
    public boolean isGET() {
        return this == GET;
    }
}
