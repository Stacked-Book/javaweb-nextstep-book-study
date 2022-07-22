package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import static util.RequestHandlerUtil.*;


public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private int contentLength=0;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            // 요청메시지의 URL부분만 자르기
            String url = splitRequestMessage(line);
            DataOutputStream dos = new DataOutputStream(out);

            // 모든 라인을 출력하기
            printAllRequestLine(br, line,dos,body);

            if (url.equals("/user/login")) {
                String requestBody = IOUtils.readData(br, contentLength);
                Map<String, String> stringStringMap = HttpRequestUtils.parseQueryString(requestBody);
                if (isMatchIdAndPw(stringStringMap)) {
                    responseCookieHeaderTrue(dos, body.length,requestBody);
                    log.info("성공시 line : {}", line);
                    log.info("로그인성공");
                    return;
                }
                else{
                    log.info("로그인 실패");
                    responseCookieHeaderFalse(dos, body.length,requestBody);
                    body = Files.readAllBytes(new File("./3~6장 실습공간/webapp/user/" + url).toPath());
                    responseBody(dos, body);
                    return;
                }
            }


            if (url.equals("/user/create")) {
                String requestBody = IOUtils.readData(br, contentLength);
                user = savePostUser(requestBody);
                response302Header(dos, body.length);
                log.info("실행되나요");
                return;
            }


            if (url.equals("/user/list.html") && HttpRequestUtils.parseCookies(line).containsKey("Cookie: logined")) {
                /*
                    쿠키존재, 로그인성공
                 */
                if (HttpRequestUtils.parseCookies(line).containsValue("true")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(DataBase.findAll());
//                    Collection<User> allMember = DataBase.findAll().;
//                    System.out.println(allMember);
                }

                /*
                    쿠키없음, 로그인실패
                 */
                else if(HttpRequestUtils.parseCookies(line).containsValue("false")){
                    response302Loginhtml(dos, body.length);
                }
            }


            if (user == null) {
                body = Files.readAllBytes(new File("./3~6장 실습공간/webapp" + url).toPath());
            }

            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Loginhtml(DataOutputStream dos, int length) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: http://localhost:8080/user/login.html\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + length + "\r\n");
            dos.writeBytes("\r\n");

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, int length) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + length + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void printAllRequestLine(BufferedReader br, String line, DataOutputStream dos, byte[] body) throws IOException {
        while (!"".equals(line)) {
            if (line == null) {
                throw new RuntimeException("");
            }

            line = br.readLine();
            if (line.startsWith("Content-Length")) {
                String[] split = line.split(":");
                this.contentLength = Integer.parseInt(split[1].trim());
            }
            log.debug("line= {}" , line);
        }
    }

    private boolean isMatchIdAndPw(Map<String, String> stringStringMap) {
        return DataBase.findUserById(stringStringMap.get("userId")).getUserId().equals(stringStringMap.get("userId"))
                && DataBase.findUserById(stringStringMap.get("userId")).getPassword().equals(stringStringMap.get("password"));
    }

    private void responseCookieHeaderFalse(DataOutputStream dos, int length, String body) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: http://localhost:8080/login_failed.html\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Set-Cookie: logined=false\r\n");
            dos.writeBytes("Content-Length: " + length + "\r\n");
            dos.writeBytes("\r\n");
            dos.writeBytes(body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseCookieHeaderTrue(DataOutputStream dos, int length, String body) {
        try {
            dos.writeBytes("HTTP/1.1 302 FOUND \r\n");
            dos.writeBytes("Location: http://localhost:8080/index.html\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Set-Cookie: logined=true\r\n");
            dos.writeBytes("Content-Length: " + length + "\r\n");
            dos.writeBytes("\r\n");
            dos.writeBytes(body);
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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
