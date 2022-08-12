package util.request;

public class Line {
    private final Method method;
    private final Url url;
    private final Version version;

    public Line(Method method, Url url, Version version) {
        this.method = method;
        this.url = url;
        this.version = version;
    }

    public Method getMethod() {
        return method;
    }

    public Url getUrl() {
        return url;
    }

    public Version getVersion() {
        return version;
    }
}
