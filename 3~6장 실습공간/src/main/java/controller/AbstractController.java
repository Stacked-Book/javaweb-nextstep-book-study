package controller;

import constants.HttpMethod;
import request.HttpRequest;
import response.HttpResponseImpl;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponseImpl response) {
        HttpMethod method = request.getMethod();

        if (method.isPost()) {
            doPost(request, response);
        } else {
            doGet(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponseImpl response) {

    }

    protected void doGet(HttpRequest request, HttpResponseImpl response) {

    }
}
