package webserver;

import db.DataBase;
import http.request.HttpRequest;
import http.request.RequestParser;
import http.response.ResponseParser;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

public class V1RequestHandler implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(V1RequestHandler.class);
    private final Socket connection;

    public V1RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = RequestParser.parser(in);
            ResponseParser responseParser = new ResponseParser(out);

            log.info("Parse Request : {}", httpRequest);

            if (httpRequest.url().equals("/user/create")) {
                User user = new User(
                    httpRequest.bodyParameter("userId"),
                    httpRequest.bodyParameter("password"),
                    httpRequest.bodyParameter("name"),
                    httpRequest.bodyParameter("email"));
                log.debug("user : {}", user);
                DataBase.addUser(user);
                responseParser.sendRedirect("/index.html");
            } else if (httpRequest.method().is("GET") && httpRequest.url().equals("/user/login")) {
                User user = DataBase.findUserById(
                    httpRequest.parameter("userId")
                );
                if (user == null) {
                    responseParser.loginFailed();
                } else if (requireNonNull(user).login(httpRequest.parameter("password"))) {
                    responseParser.loginSuccess();
                } else {
                    responseParser.loginFailed();
                }
            } else if (httpRequest.method().is("POST") && httpRequest.url().equals("/user/login")) {
                User user = DataBase.findUserById(
                    httpRequest.bodyParameter("userId")
                );
                if (user == null) {
                    responseParser.loginFailed();
                } else if (requireNonNull(user).login(httpRequest.bodyParameter("password"))) {
                    responseParser.loginSuccess();
                } else {
                    responseParser.loginFailed();
                }
            } else if (httpRequest.url().equals("/user/list")) {
                if (!httpRequest.isLogined()) {
                    responseParser.sendRedirect("/user/login.html");
                    return;
                }

                Collection<User> users = DataBase.findAll();
                StringBuilder sb = new StringBuilder();
                sb.append("<table border = '1'>");
                for (User user : users) {
                    sb.append("<trr>");
                    sb.append("<td><").append(user.getUserId()).append("/td>");
                    sb.append("<td><").append(user.getName()).append("/td>");
                    sb.append("<td><").append(user.getEmail()).append("/td>");
                    sb.append("</tr>");
                }
                responseParser.forwardBody(sb.toString());
            } else {
                responseParser.forward(httpRequest.url());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
