package requestcontroller;

import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;

public interface Controller {
    default void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        selectMethod(httpRequest, httpResponse);
    }

    default void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {}
    default void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {}

    private void selectMethod(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpMethod httpMethod = httpRequest.getMethod();
        if (httpMethod.isGET()) {
            doGet(httpRequest, httpResponse);
        } else if (httpMethod.isPost()) {
            doPost(httpRequest, httpResponse);
        }
    }
}
