package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static util.RequestHandlerUtil.*;


public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private User user;
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        byte[] body = "Hello World".getBytes(StandardCharsets.UTF_8);

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse(out);

            String url = httpRequest.getPath();

            if (url.equals("/user/login")) {
                if (isMatchIdAndPw(httpRequest.getParamsMap())) {
                    httpResponse.addHeader("Set-Cookie", "logined=true");
                    httpResponse.sendRedirect("/index.html");
                    log.info("로그인성공");
                    return;
                }
                else{
                    log.info("로그인 실패");
                    httpResponse.sendRedirect("/user/login_failed.html");
                    return;
                }
            }

            if (url.equals("/user/create")) {
                user = savePostUser(httpRequest.getPath());
                httpResponse.sendRedirect("index.html");
                log.info("/user/create 성공");
                return;
            }

            if (url.equals("/user/list.html") && httpRequest.getHeader("Cookie").contains("logined")) {
                /*
                    쿠키존재, 로그인성공
                 */
                if (httpRequest.getHeader("Cookie").contains("logined=true")) {
                    Collection<User> users = DataBase.findAll();
                    StringBuilder sb = new StringBuilder();
                    sb.append("<table border='1'>");
                    for (User user : users) {
                        sb.append("<tr>");
                        sb.append("<td>" + user.getUserId() + "</td>");
                        sb.append("<td>" + user.getName() + "</td>");
                        sb.append("<td>" + user.getEmail() + "</td>");
                        sb.append("</tr>");
                    }
                    sb.append("</table>");
                    httpResponse.forwardBody(sb.toString());
                }

                /*
                    쿠키없음, 로그인실패
                 */
                else if (httpRequest.getHeader("Cookie").contains("logined=false")) {
                    httpResponse.sendRedirect("/user/login.html");
                }
            }

            if (user == null) {
                body = Files.readAllBytes(new File("./3~6장 실습공간/webapp" + url).toPath());
            }
            httpResponse.forward(url);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private boolean isMatchIdAndPw(Map<String, String> stringStringMap) {
        return DataBase.findUserById(stringStringMap.get("userId")).getUserId().equals(stringStringMap.get("userId"))
                && DataBase.findUserById(stringStringMap.get("userId")).getPassword().equals(stringStringMap.get("password"));
    }
}
