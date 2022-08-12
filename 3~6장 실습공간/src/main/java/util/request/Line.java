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

    public static Line of(String line) {
        String[] lines = line.split(" ");
        return new Line(Method.valueOf(lines[0]), Url.of(lines[1]), Version.of(lines[2]));
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

    @Override
    public String toString() {
        return "Line{" +
            "method=" + method +
            ", url=" + url +
            ", version=" + version +
            '}';
    }
}
