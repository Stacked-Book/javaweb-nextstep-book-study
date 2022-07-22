package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

            // 모든 라인을 출력하기
            printAllRequestLine(br, line);

            if (this.contentLength != 0) {
                String requestBody = IOUtils.readData(br, contentLength);
                user = savePostUser(requestBody);
            }

            if (user == null) {
                body = Files.readAllBytes(new File("./3~6장 실습공간/webapp" + url).toPath());
            }

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void printAllRequestLine(BufferedReader br, String line) throws IOException {
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
