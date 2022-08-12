package util.request;

public enum Method {
    GET,
    POST;

    public boolean is(final String method) {
        return this.name().equals(method);
    }
}
