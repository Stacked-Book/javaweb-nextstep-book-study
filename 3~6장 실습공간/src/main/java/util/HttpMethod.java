package util;

public enum HttpMethod {
    GET,
    POST;

    public boolean is(final String method) {
        return this.name().equals(method);
    }
}
