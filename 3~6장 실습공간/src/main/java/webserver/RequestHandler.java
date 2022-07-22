package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
//            String[] token = reqData.split(("\\s+"));
            String[] token = Arrays.stream(reqData.split("\\s+"))
                    .map(String::trim)
                    .toArray(String[]::new);
            String url = token[1];



            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = resDataFromUrl(url);

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
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
