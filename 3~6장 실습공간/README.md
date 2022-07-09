# 실습을 위한 개발 환경 세팅
- 저자의 원본코드는  https://github.com/slipp/web-application-server 를 참고
- fork 후 각자 브랜치를 생성해서 Pull requests 하는방식 

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.
