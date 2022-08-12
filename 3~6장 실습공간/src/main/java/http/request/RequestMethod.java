package http.request;

public enum RequestMethod {
    GET,
    POST;

    public boolean is(final String method) {
        return this.name().equals(method);
    }
}
