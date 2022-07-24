package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
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

            String reqData = getDataFromInputStream(in);
            String[] token = Arrays.stream(reqData.split("\\s+"))
                    .map(String::trim)
                    .toArray(String[]::new);
            String httpMethod = token[0];
            String url = token[1];
            Map<String, String> value = new HashMap<>();

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

                }

            }
            controlUrl(httpMethod,url,value);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = resDataFromUrl(url);

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch ( IOException e ) {
            log.error(e.getMessage());
        }
    }

    /**
     * httpMethod, url에 따라 분기처리하기 위한 컨트롤러
     * */
    private void controlUrl(String httpMethod, String url, Map<String,String> value) {
        if(httpMethod.equals("GET")) {
            switch (url) {
                case "/user/create" -> {
                    UserUtils.saveUser(value);
                }

            }
        }
        if(httpMethod.equals("POST")) {
            switch (url) {
                case "/user/create" -> UserUtils.saveUser(value);
            }
        }
    }

    /**
     * url로부터 쿼리 파라미터가 있는지 확인하는 메서드
     * */
    private boolean isOwnQueryData(String url) {
        return url.contains("?");
    }

    /**
     * InputStream으로부터 데이터 추출하는 메서드
     * */
    private String getDataFromInputStream(InputStream in) throws IOException {
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(reader);

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

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
