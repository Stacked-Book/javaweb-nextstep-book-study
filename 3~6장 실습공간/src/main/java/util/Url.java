package util;

public enum Url {
    INDEX_PAGE("/index.html"),
    REGISTER_PAGE("/user/form.html"),
    REGISTER("/user/create"),
    LOGIN_PAGE("/user/login.html"),
    LOGIN("/user/login"),
    LOGIN_FAILED_PAGE("/user/login_failed.html"),
    USER_LIST_PAGE("/user/list.html"),
    USER_LIST("/user/list"),
    CSS_STYLE("/css/styles.css"),
    BOOTSTRAP("/css/bootstrap.min.css");
    private static final String QUERY = "?";
    private final String url;

    Url(final String url) {
        this.url = url;
    }

    public static boolean isCSS(final String url) {
        return CSS_STYLE.is(url) || BOOTSTRAP.is(url);
    }

    public boolean is(final String url) {
        if (url.contains(QUERY)) {
            return this.url.equals(url.substring(0, url.indexOf(QUERY)));
        }
        return this.url.equals(url);
    }
}
