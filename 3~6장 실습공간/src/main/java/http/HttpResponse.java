package http;

public interface HttpResponse {

    void forward(String url);

    void forwardBody(String body);

    default void sendRedirect(String redirectUrl) {}

    void writeHeaders();

    void addHeader(String key, String value);

    default void response200Header(int lengthOfBodyContent) {}

    default void responseBody(byte[] body) {}

}
