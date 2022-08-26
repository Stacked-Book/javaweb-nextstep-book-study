package request;

import constants.HttpMethod;
import util.HttpCookie;
import util.HttpSession;

public abstract class HttpRequest {


    protected HttpRequest() {}

    public abstract HttpMethod getMethod();

    public abstract String getPath();

    public abstract String getParameter(String name);

    public abstract String getHeader(String name);

    public abstract HttpCookie getCookies();

    public abstract HttpSession getSession();

}
