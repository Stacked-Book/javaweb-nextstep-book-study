package controller;

import request.HttpRequest;
import response.HttpResponseImpl;

public interface Controller {

    void service(HttpRequest request, HttpResponseImpl response);
}
