package util.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Url {
    private static final String REGEX = "^(?<protocol>[a-zA-Z0-9]*://)?(?<authority>[a-zA-Z0-9.]+)?(?<port>:[0-9]+)?(?<path>/?[a-zA-Z/.&]+)?(?<query>\\?[a-zA-Z0-9=&]+)?$";
    private static final Pattern pattern = Pattern.compile(REGEX);
    private static final String PATH = "path";
    private static final String QUERY = "query";
    private final String path;
    private final String query;

    public Url(String path, String query) {
        this.path = path;
        this.query = query;
    }

    public static Url of(String url) {
        Matcher matcher = pattern.matcher(url);

        if (!matcher.find()) {
            throw new URIParsingException();
        }
        String path = matcher.group(PATH);
        String query = matcher.group(QUERY);

        return new Url(path, query);
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }


    @Override
    public String toString() {
        return "Url{" +
            " path='" + path + '\'' +
            ", query='" + query + '\'' +
            '}';
    }
}
