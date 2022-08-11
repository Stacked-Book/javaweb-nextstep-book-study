# 실습을 위한 개발 환경 세팅
- 저자의 원본코드는  https://github.com/slipp/web-application-server 를 참고
- fork 후 각자 브랜치를 생성해서 Pull requests 하는방식 

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.


## 모델링

### HttpRequest

- 클라이언트 요청 데이터를 담는 InputStream을 생성자로 받아 HTTP 메소드, 헤더, 본문을 분리하는 작업을 한다.
- 헤더에는 Map<String, String>에 저장해 관리하고 getHeader("headerName") 메서드를 통해 접근 가능하도록 구현한다.
- GET과 POST 메서드에 따라 전달되는 인자를 Map<String, String>에 저장해 관리하고, getParameter("param") 메서드를 통해 접근 가능하도록 구현한다.

### 모델링

### HttpRequest

- 행위
  - method를 반환합니다.
  - url 정보를 반환합니다.
  - protocol version을 반환합니다.
  - header key 값에 저장된 value를 반환합니다.
  - body 정보를 반홥합니다.
    - header의 `Content-Length` 값이 1 이상이어야 합니다.

### RequestLine
  - 상태
    - RequestLine은 RequestMethod, RequestUrl, ProtocolVersion 정보를 가집니다.
  - 행위
    - method를 반환합니다.
    - url 정보를 반환합니다.
    - protocol version을 반환합니다. 

### RequestHeader

- 상태
  - 요청 header 정보를 가집니다. 
- 행위
  - 각 header key 값에 저장된 value를 반환합니다.

### RequestBody

- 상태
  - 요청 body 정보를 가집니다.
- 행위
  - body 정보를 반홥합니다.
