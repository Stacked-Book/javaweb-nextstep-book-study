package http.header;

public enum MediaType {
    TEXT_HTML("text/html"),
    APPLICATION_JSON("application/json");

    private String value;

    MediaType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
