package util.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Url {
    private static final String REGEX = "(?<protocol>[a-zA-z0-9]*://)(?<authority>[a-zA-z0-9.]*)(?<port>:[0-9]+)*(?<path>/*[a-zA-z/.]+)(?<query>\\?[a-zA-Z0-9=]+)*(?<fragment>#[a-zA-Z0-9]+)*";
    private static final Pattern pattern = Pattern.compile(REGEX);
    private static final String PROTOCOL = "protocol";
    private static final String AUTHORITY = "authority";
    private static final String PORT = "port";
    private static final String PATH = "path";
    private static final String QUERY = "query";
    private static final String FRAGMENT = "fragment";
    private final String protocol;
    private final String authority;
    private final String port;
    private final String path;
    private final String query;
    private final String fragment;

    public Url(String protocol, String authority, String port, String path, String query, String fragment) {
        this.protocol = protocol;
        this.authority = authority;
        this.port = port;
        this.path = path;
        this.query = query;
        this.fragment = fragment;
    }

    public static Url of(String url) {
        Matcher matcher = pattern.matcher(url);

        if (!matcher.find()) {
            throw new URIParsingException();
        }

        String protocol = getString(matcher, PROTOCOL);
        String authority = getString(matcher, AUTHORITY);
        String port = getString(matcher, PORT);
        String path = getString(matcher, PATH);
        String query = getString(matcher, QUERY);
        String fragment = getString(matcher, FRAGMENT);

        return new Url(protocol, authority, port, path, query, fragment);
    }

    private static String getString(Matcher matcher, String name) {
        return matcher.group(name);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getAuthority() {
        return authority;
    }

    public String getPort() {
        return port;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public String getFragment() {
        return fragment;
    }
}
