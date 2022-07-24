package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;
import util.UserUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);

            String reqData = getDataFromInputStream(br);
            String[] token = Arrays.stream(reqData.split("\\s+"))
                    .map(String::trim)
                    .toArray(String[]::new);
            String httpMethod = token[0];
            String url = token[1];
            int contentLength = 0;
            boolean islogin = false;
            Map<String, String> value = new HashMap<>();

            /**
             * GET 요청일 경우 url만 따로 추출 쿼리 스트링 값 value로 추출
             * POST 요청일 경우 IOUtils 사용하여 body 내의 데이터 추출
             * */
            switch (httpMethod) {
                case "GET" -> {
                    if(isOwnQueryData(url)){
                        int index = url.indexOf("?");
                        String requestPath = url.substring(0,index);
                        String params = url.substring(index+1);

                        value = HttpRequestUtils.parseQueryString(params);
                        url = requestPath;

                    }
                }
                case "POST" -> {
                    if(reqData.contains("Content-Length")) {
                        contentLength = getContentLength(reqData);
                    }
                    String body = IOUtils.readData(br, contentLength);
                    value = HttpRequestUtils.parseQueryString(body);
                }
            }

            islogin = isLogin(reqData);

            String specificReturnUrl = controlUrl(islogin, httpMethod,url,value);

            /**
             * controlUrl에 의해 처리가 되었을 경우 해당하는 header, body 세팅
             * */
            if(specificReturnUrl != null) {
                DataOutputStream dos = new DataOutputStream(out);

                if(url.equals("/user/create")){
                    byte[] body = resDataFromUrl(specificReturnUrl);
                    response302Header(dos, specificReturnUrl);
                    responseBody(dos, body);
                }

                if(url.equals("/user/login")) {
                    if(specificReturnUrl.equals("/index.html")) {
                        byte[] body = resDataFromUrl(specificReturnUrl);
                        response302LoginHeader(dos, specificReturnUrl);
                        responseBody(dos, body);
                    } else {
                        byte[] body = resDataFromUrl(specificReturnUrl);
                        response200Header(dos, body.length);
                        responseBody(dos, body);
                    }
                }

                if(url.equals("/user/list")) {
                    if(specificReturnUrl.equals("/user/login.html")){
                        byte[] body = resDataFromUrl(specificReturnUrl);
                        response200Header(dos, body.length);
                        responseBody(dos, body);
                    } else {
                        Collection<User> users = UserUtils.findAllUser();
                        byte[] body = makeUserTable(users).toString().getBytes(StandardCharsets.UTF_8);
                        response200Header(dos, body.length);
                        responseBody(dos, body);
                    }
                }
            /**
             * controlUrl에 의해 처리가 되지않았을 경우 url에 명시된 파일이 있는지. 없으면 Hello World 반환
             * */
            } else if (url.endsWith(".css")) {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = resDataFromUrl(url);
                response200CssHeader(dos, body.length);
                responseBody(dos, body);
            } else {
                DataOutputStream dos = new DataOutputStream(out);
                byte[] body = resDataFromUrl(url);
                response200Header(dos, body.length);
                responseBody(dos, body);
            }

        } catch ( IOException e ) {
            log.error(e.getMessage());
        }
    }

    /**
     * httpMethod, url에 따라 분기처리하기 위한 컨트롤러
     * */
    private String controlUrl(boolean isLogin, String httpMethod, String url, Map<String,String> value) {
        if(httpMethod.equals("GET")) {
            switch (url) {
                case "/user/create" -> {
                    UserUtils.addUser(value);
                    return "/index.html";
                }
                case "/user/list" -> {
                    if(isLogin){
                        return "getUser";
                    } else{
                        return "/user/login.html";

                    }
                }
            }
        }
        if(httpMethod.equals("POST")) {
            switch (url) {
                case "/user/create" -> {
                    UserUtils.addUser(value);
                    return "/index.html";
                }
                case "/user/login" -> {
                    boolean isUser = UserUtils.isUser(value);
                    if(isUser) return "/index.html";
                    else return "/user/login_failed.html";
                }
            }
        }
        return null;
    }

    private StringBuilder makeUserTable(Collection<User> users) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        for(User user : users ) {
            sb.append("<tr>");
            sb.append("<td>" + user.getUserId() + "</td>");
            sb.append("<td>" + user.getName() + "</td>");
            sb.append("<td>" + user.getEmail() + "</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb;
    }

    /**
     * url로부터 쿼리 파라미터가 있는지 확인하는 메서드
     * */
    private boolean isOwnQueryData(String url) {
        return url.contains("?");
    }

    private boolean isLogin(String reqData) {
        String cookie = getCookie(reqData);
        if(cookie != null) {
            Map<String, String> cookies = HttpRequestUtils.parseCookies(cookie);
            String value = cookies.get("logined");
            return Boolean.parseBoolean(value);
        }
        return false;
    }

    private String getCookie(String reqData) {
        if(reqData.contains("Cookie")) {
            String[] token = Arrays.stream(reqData.split("\\s+"))
                    .map(String::trim)
                    .toArray(String[]::new);
            for(int i =0; i< token.length; i ++) {
                if(token[i].contains("Cookie"))
                    return token[i+1];
            }
        }
        return null;
    }

    private int getContentLength(String reqData) {
        String[] token = Arrays.stream(reqData.split("\\s+"))
                .map(String::trim)
                .toArray(String[]::new);
        for(int i =0; i< token.length; i ++) {
            if(token[i].contains("Content-Length"))
                return Integer.parseInt(token[i+1]);
        }
        return 0;
    }
    /**
     * InputStream으로부터 데이터 추출하는 메서드
     * */
    private String getDataFromInputStream(BufferedReader br) throws IOException {

        String headerLine = "";
        String line = "";

        do {
            line = br.readLine();
            headerLine += line+ " ";
            if(line == null){
                break;
            }
        } while(!(line).equals("") );
        return headerLine;
    }

    /**
     * 요청 url로부터 해당 파일이 있을 경우 추출하는 메서드
     * */
    private byte[] resDataFromUrl(String url) throws IOException {

        if(url == null || url == "") return "Hello World".getBytes();
        if (Files.notExists(new File("./webapp" + url).toPath()))  return "Hello World".getBytes();
        if (Files.isDirectory(new File("./webapp" + url).toPath()))  return "Hello World".getBytes();

        return Files.readAllBytes(new File("./webapp" + url).toPath());
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

    private void response200CssHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/css\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: "+ url + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302LoginHeader(DataOutputStream dos, String url) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Set-Cookie: logined=true \r\n");
            dos.writeBytes("Location: "+ url + "\r\n");
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
