package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;


public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private HttpRequestUtils httpRequestUtils;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();

            if(line == null) {
                return;
            }

            String[] tokens = line.split(" ");

            String url = httpRequestUtils.getUrl(tokens);
            String method = httpRequestUtils.getMethod(tokens);

            Map<String, String> headers = new HashMap<String, String>();
            while(!"".equals(line)) {
                log.debug("header : {}", line);
                line = br.readLine();
                String[] headerTokens = line.split(": ");
                if (headerTokens.length == 2) {
                    headers.put(headerTokens[0], headerTokens[1]);
                }
            }
            log.debug("Content-Length : {}", headers.get("Content-Length"));
            DataOutputStream dos = new DataOutputStream(out);

            if (url.startsWith("/user/create")) {
                String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                log.debug("Request Body : {}", requestBody);
                Map<String, String> params = httpRequestUtils.parseQueryString(requestBody);
                User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
                log.debug("User : {}", user);
                DataBase.addUser(user);

                response302Header(dos);
            } else if(url.equals("/user/login")) {
                String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
                log.debug("Request Body : {}", requestBody);
                Map<String, String> params = httpRequestUtils.parseQueryString(requestBody);
                log.debug("userId : {}, password : {}", params.get("userId"), params.get("password"));
                User user = DataBase.findUserById(params.get("userId"));
                if (user == null) {
                    log.debug("User Not Found");
                    response302Header(dos);
                }
                if (params.get("password").equals(user.getPassword())) {
                    log.debug("login success");
                    response302WithCookie(dos, "logined=true");
                } else {
                    log.debug("Password Mismatch");
                    response302WithCookie(dos, "logined=false");
                }

            } else if (url.equals("/user/list")) {
                Map<String, String> cookie = httpRequestUtils.parseCookies(headers.get("Cookie"));
                Boolean flag = Boolean.parseBoolean(cookie.get("logined"));

                if (flag) {
                    StringBuilder sb = new StringBuilder();

                    Collection<User> userList = DataBase.findAll();

                    for(User user : userList) {
                        sb.append("<li> ID : " + user.getUserId() + ", Name : " + user.getName() + ", Email : " + user.getEmail() + "</li>");
                    }
                    String html = sb.toString();
                    byte[] body = html.getBytes();
                    response200Header(dos, body.length);
                    responseBody(dos, body);
                } else {
                    log.debug("Cookie is missing");
                    response302Header(dos);
                }

            } else if (url.endsWith(".css")) {
                byte[] body = Files.readAllBytes(new File("./3~6장 실습공간/webapp" + url).toPath());
                response200HeaderWithCss(dos, body.length);
                responseBody(dos, body);
            } else {
                byte[] body = Files.readAllBytes(new File("./3~6장 실습공간/webapp", url).toPath());
                response200Header(dos, body.length);
                responseBody(dos, body);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200HeaderWithCss(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302WithCookie(DataOutputStream dos, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: /index.html\r\n");
            dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
