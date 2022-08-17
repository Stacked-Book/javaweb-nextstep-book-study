package controller;

import request.HttpRequest;
import response.HttpResponse;

public interface Controller {

    void service(HttpRequest request, HttpResponse response);
}
