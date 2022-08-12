package support.request;

public final class RequestLine {
    private final RequestMethod method;
    private final RequestUrl url;
    private final RequestVersion version;

    public RequestLine(RequestMethod method, RequestUrl url, RequestVersion version) {
        this.method = method;
        this.url = url;
        this.version = version;
    }

    public static RequestLine of(String line) {
        String[] lines = line.split(" ");
        return new RequestLine(RequestMethod.valueOf(lines[0]), RequestUrl.of(lines[1]), RequestVersion.of(lines[2]));
    }

    public RequestMethod getMethod() {
        return method;
    }

    public RequestUrl getUrl() {
        return url;
    }

    public RequestVersion getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Line{" +
            "method=" + method +
            ", url=" + url +
            ", version=" + version +
            '}';
    }
}
