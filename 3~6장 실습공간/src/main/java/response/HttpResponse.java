package response;

public interface HttpResponse {

    public void forward(String url);
    public void forwardBody(String body);
    public void response200Header();
    public void responseBody(byte[] body);
    public void sendRedirect(String path);

}
