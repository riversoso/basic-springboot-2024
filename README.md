# basic-springboot-2024
 Java 빅데이터 개발자 과정 Spring Boot 학습 리포지토리

## 1일차
- Spring Boot 개요
    - 개발환경, 개발 난이도를 낮추는 작업
    - Servlet > EJB > JSP > Spring(부흥기) > Spring Boot(끝판왕!!)
    - 장점
        - Spring의 기술을 그대로 사용가능(마이그레이션 간단)
        - JPA를 사용하면 ERD나 DB설계를 하지 않고도 손쉽게 DB 생성
        - Tomcat Webserver가 내장(따로 설치필요x)
        - 서포트 기능 다수 존재(개발을 쉽게 도와줌)
        - JUnit 테스트, Log4J2 로그도 모두 포함
        - JSP, **Thymeleaf**, Mustache 등.. 편하게 사용가능
        - DB 연동이 무지 쉽다

    - MVC
        <img src="https://raw.githubusercontent.com/riversoso/basic-springboot-2024/main/images/sp002.png" width="730">

- Spring Boot 개발환경 설정
    - Java JDK 확인 > 17버전 이상
	- https://jdk.java.net/archive/
	- 시스템 속성>고급>환경변수 중 JAVA_HOME 설정

    - Visual Studio Code
	- VSCodeUserSetup-x64-1.90.0.exe 아님 설치하지 말 것
	- VSCodeSetup-x64-1.90.0.exe 로 설치할 것
	- Extensions > Korean 검색, 설치
	- Extensions > Java 검색, Extensions Path
		-Debugger for Java 등 여섯개 확장팩이 같이 설치
	- Extensions > Spring 검색, Spring Boot Extensions Pack 설치
		- Spring Initializr Java Suport 등 3개 확장팩 같이 설치
	- Extensions > Gradle for Java 검색, 설치
    - Gardle build tool 설치 고려
	- https://gradle.org/releases/
    - Oracle latest version Docker - 보류

- Spring Boot 프로젝트 생성
    - 메뉴보기 > 명령 팔레트(ctrl + shift + p)
        - Spring Initializr: Create a Gradle Project
        - Specify Spring Boot version : 3.2.6
        - specify project language : Java
        - Input Group Id : com.riversoso (개인적 변경할 것)
        - Input Artifact Id : spring01
        - Specify packaging type : Jar
        - Specify Java version : 17
        - choose dependencies : Selectde 0 dependencies
        - 폴더 선택 Diaglog 팝업 : 원하는 폴더 선택 Generate ... 버튼 클릭
        - 오른쪽 하단 팝업에서 open 버튼 클릭
        - git 설정 옵션, Language Support for Java(TM) by Red Hat 설정 항상버튼 클릭

    - troubleShooting
        1. 프로젝트 생성이 진행되다 Gradle Connection 에러가 뜨면,
            - Extensions > Gradle for Java 를 제거
            - VS Code 재시작한 뒤 프로젝트 재생성
        2. Gradle 빌드시 버전 에러로 빌드가 실패하면
            - Gradle build tool 사이트에서 에러에 표시된 버전의 Gradle bt 다운로드
            - 개발 컴퓨터에 설치
        3. ':compileJava' execution failed...
            - JDK 17 ... error 메세지
            - Java JDK 잘못된 설치 x86(32bit) x64비트 혼용 설치
            - eclipse adoptium JDK 17 새로 설치, 시스템 환경설정
            - VS Code 재시작 
            - build.gradle springBoot Framework 버전을 다운 3.30 -> 3.1.5

    
    - 프로젝트 생성후
        - build.gradle 확인
        - src/main/resources/application.properties(또는 .yml) 확인
        - src/java/groupid/arifactid/Java 소스파일 위치, 작업
        - src/main/resources/ 프로젝트설정, 파일, 웹 리소스 파일(css, js, html, jsp, ...)
        - Spring01Application.run | debug 메뉴
        - Gradle 빌드
            - 터미널에서 .\gradlew.bat 실행
            - Gradle for java(코끼리 아이콘) > Tasks > Build > Build play icon(Run task) 실행
        - Spring Boot Dashboard
            - Apps > Spring01 Run | Debug 중에서 하나 아이콘 클릭 서버 실행
            - 디버그로 실행해야 Hot code replace 가 동작!!

            <img src="https://raw.githubusercontent.com/riversoso/basic-springboot-2024/main/images/sp001.png" width="350">
        - 브라우저 변경설정
            - 설정 (ctrl + ,) > browser > Spring > Dashboard Open With 'Internal' -> 'external'로 변경
            - Chrome 을 기본브라우저 사용 추천

## 2,3일차
- Oracle 도커로 설치
    - Docker는 Virtual Machine을 업그레이드 한 시스템
    - 윈도우에 서비스 내(services.msc) Oracle 관련 서비스 종료
    - Docker에서 Oracle 이미지 컨테이너를 다운로드 후 실행
    - Docker 설치시 오류 Docker Desktop - WSL Update failed
        - Docker Desktop 실행종료 후
        - Window 업데이트 실행 최신판 재부팅
        - https://github.com/microsoft/WSL/releases, wsl.2.x.x.x64.msi 다운로드 설치한 뒤
        - Docker Desktop 재실행 
    - Oracle 최신판 설치
    ```
    > docker --version
    Docker version 26.1.1, build 4cf5afa
    > docker pull container-registry.oracle.com/database/free:latest
    latest: ....
    ... : Download complete
    > docker images
    REPOSITORY                                    TAG       IMAGE ID       CREATED       SIZE
    container-registry.oracle.com/database/free   latest    7510f8869b04   7 weeks ago   8.7GB
    > docker run -d -p 1521:1521 --name oracle container-registry.oracle.com/database/free
    ...
    > docker logs oracle
    ...

    ...
    > docker exec -it oracle bash
    ```
    #########################
    DATABASE IS READY TO USE!
    #########################

    - Oracle system 사용자 비번 설정
    ```shell
    bash-4.4$ ./setPassword.sh oracle
    ```
    - Oracle 접속확인
        - DBeaver 탐색기 > Create > Connection


- Database 설정
    - Oracle - 운영시 사용할 DB
    - Oracle PKNUSB / pknu_P@ss 로 생성
        - 콘솔(도커 / 일반 Oracle)
        ```shell
        > sqlplus system/password
        SQL> SELECT name from v$database;
        // 서비스명 확인
        // 최신버전에서 사용자 생성시 C## prefix 방지 쿼리
        SQL> ALTER SESSION SET "_ORACLE_SCRIPT"=true;
        ```
        // 사용자 생성
        SQL> create user pknusb identified by "pknu_p@ss";
        // 사용자 권한
        SQL> grant CONNECT, RESOURCE, CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE VIEW to pknusb;
        // 사용자 계정 테이블 공간설정, 공간쿼터
        SQL> alter user pknusb default tablespace users;
        SQL> alter user pknusb quota unlimited on users;

    - H2 DB - Spring Boot에서 손쉽게 사용가능한 Inmemory DB, Oracle, Mysql, SQLServer과 쉽게 호환
    - MYSQL -Optional 설명할 DB
    
- Spring Boot + MyBatis 프로젝트
    - application name : spring02
    - Spring Boot 3.2.6 선택 -3.3.x 에는 MyBatis 없음
    - Dependency
        - Spring Boot DevTools
        - Lombok
        - Spring Web
        - Thymeleaf
        - Oracle Driver
        - MyBatis starter

    - build.gradle 확인
    - application.properties 추가작성
    - Dependency 중 DB(H2, Oracle, MySQL)가 선택시 application.properties에 DB설정 안되면 서버 실행 안됨
    
    ```properties
    spring.application.name=spring02

    ## 포트변경
    server.port=8091

    ## 로그색상
    spring.output.ansi.enabled=always

    ## 수정사항이 있으면 서버 자동 재빌드 설정
    spring.devtools.livereload.enabled=true
    spring.devtools.restart.enabled=true

    ## 로그레벨 설정
    logging.level.org.springframework=info
    logging.level.org.zerock=debug

    ## Oracle 설정
    spring.datasource.username=pknusb
    spring.datasource.password=pknu_P@ss
    spring.datasource.url=jdbc:oracle:thin@localhost:11521:FREE
    spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

    ## MyBatis 설정
    ## mapper 폴더 밑에 여러가지 폴더가 내재, 확장자는 .xml이지만 파일명은 뭐든지
    mybatis.mapper-locations=classpath:mapper/**/*.xml
    mybatis.type-aliases-package=com.riversoso.spring02.mapper
    ```

    - MyBatis 적용
        - SpringBoot 이전 resource/WEB-INF 위치에 root-context.xml에 DB, MyBatis 설정
        - SpringBoot 이후 application.properties + Config.java 로 변경

    - MyBatis 개발시 순서
        0. application.properties jdbc:oracle:thin:@localhost:11521:FREE, thin 뒤 : 이 삭제되어 있었음
        1. Database 테이블 설정
        2. MyBatis 설정 -> /config/MyBatisConfig.java
        3. 테이블과 일치하는 클래스(domain, entity, dto, vo(readonly), etc...) 생성
            - 테이블 컬럼 _는 Java클래스는 사용안함
        4. DB에 데이터를 주고 받을 수 있는 클래스(dao, **mapper**, repository ...) 생성
            - 쿼리를 클래스 내 작성가능, xml로 분리가능
        5. (Model)분리했을 경우 /resources/mapper/클래스.xml 생성, 쿼리 입력
        6. 서비스 인터페이스 /service/*Service.java, 서비스 구현 클래스 생성 /service/*ServiceImpl.java 생성
        7. 사용자 접근하는 컨트롤러 클래스 생성 -> @Controller 변경 가능
        8. (Controller) 경우에 따라 @SpringBootApplication 클래스에 SqlSessionFactory 빈을 생성 메서드 작성--
        9. (View) /resource/tempates/ Thymeleaf html 생성, 작성

## 4일차

- Spring Boot JPA + Oracle + Thymeleaf + React

  - JPA -> DB 설계를 하지 않고 엔티티 클래스를 DB.로 자동생성 해주는 기술, Query로 만들 필요 x
  - H2 -> Oracle, MySQL, SQLServer등 과 달리Inmemory DB, 스프링 부트 실행되면 같이 실행되는 DB
    개발 편의성, 다른 DB로 전환 시 아주 편리, 개발하는 동안 사용을 추천
  - Thymeleaf -> JSP의 단점 복잡한 템플릿 형태 + 스파게티 코드를 해소해주는 템플릿
  - Bootstrap -> 웹디자인 및 CSS의 혁신! 커스터 마이징도 가능
  - 소셜로그인 -> 구글, 카카오, 네이버 등등 소셜로 로그인 가능
  - React -> 프론트엔드를 분리, 백엔드 서버와 프론트엔드 서버 따로 관리

- SPirng Boot JPA 프로젝트 생성

  - 명령팔레트로 시작, Spring Initialzr : Create a Gradle project...
  - Spring Boot Version -> 3.2.6
  - project language -> Java
  - Group id -> com.springboot
  - Arifact Id -> backboard
  - package type -> Jar
  - Java version -> 17
  - Dependency
    1. Spring Boot DevTools
    2. Lombok
    3. Spring Web
    4. Thymeleaf
    5. Oracle Driver(later)
    6. H2 Database(later)
    7. Data JPA(later)
  - Spirng03 폴더 내에서 **Generate into this folder**

- Spring Boot JPA 프로젝트 개발
    1. (설정) build.gradle 디펜던시 확인
    2. (설정) apllication.properties 기본설정 입력(포트번호, 로그색상, 자동재빌드, 로그레벨)
    3. MVC패턴에 맞춰서 각 기능별로 폴더를 생성(controller, service, entity ...)
    4. (설정) /controller/MainController.java 생성, 기본 메서드 구현
    5. (설정) application.properties H2, JPA설정 추가
    6. (설정) 웹서버 실행 http://localhost:8091/h2-console DB연결 확인

    7. /entity/Board.java 생성
        - GenerationType 타입
            -AUTO : SpringBoot에서 자동으로 선택(x)
            -IDENTITY : MySQL, SQLServer
            -SEQUENCE : Oracle(!)
        - column 이름을 createDate로 만들면 DB에 컬럼명이 create_date 로 생성
            -컬럼명에 언더바를 안넣으려면 @column(name = "") 사용
    8. /entity/Reply.java 생성
    9. 두 엔티티간 @OneToMany, @ManyToOne을 설정
    10. 웹 서버 재시작 후 h2-console에서 테이블 생성 확인
    11. /repository/BoardRepository.java 빈 인터페이스(JpaRepository 상속) 생성
    12. /repository/ReplyRepository.java 빈 인터페이스(JpaRepository 상속) 생성
    13. ()application.properties ddl-auto=create -> ddl-auto=update 변경
    14. /test/.../repository/BoardRepositoryTest.java 생성, 테스트 메서드 작성
    15. 테스트 시작 > 웹 서버 실행 > h2-console 확인

## 5일차
-Tip
    - Java Test 중 OpenJDK 64-Bit Server VM warning: Sharing is
    - Ctrl + , (설정) > Java test Config > settings.json 편집
    ```json
    "java.test.config": {
        "vmArgs": [
            "-Xshare:off"
        ]
    }
    ```
    -저장 후 실행

- Spring Boot 프로젝트 오류처리
    - 빌드를 해도 제대로 결과가 반영이 안되면
    - GitHub Remote Repository에 모두 커밋,푸시 후
    - Local Repository를 모두 삭제 후 새로 커밋
    - 프로젝트 새로 로드, 초기화

- SpringBoot JPA 프로젝트 개발 계속
    1. jUnit 테스트로 CRUD 확인
    2. /service/BoardService.java 생성 후 getList() 메서드 작성
    3. /controller/BoardController.java 생성 후 /board/list 실행 할 수 있는 메서드 작성
    4. /templates/board/list.html 생성
        - Thymeleaf 속성
            - th:if = "${board != null}"
            - th:each = "board : ${boardList}"
            - th:text = "${board.title}"
    5. /service/BoardService.java에 getBoard() 메서드 추가
    6. /controller/BoardController/java에 /board/detail/{bno} 실행 메서드 작성
    7. /templates/board/detail/html 생성

        <img src="https://raw.githubusercontent.com/riversoso/basic-springboot-2024/main/images/sp003.png" width="730">


    8. /templates/board/detail.html에 댓글영역 추가
    9. /service/ReplyService.java 생성, 댓글 저장 메서드 작성
    10. /controller/ReplyController.java 생성, /reply/create/{bno} 포스트매핑 메서드 작성

    11. Bootstrap 적용 방법
        - 다운로드 후 프로젝트에 위치
        - CDN 링크를 추가
        - http://www.getbootstrap.com 다운로드 후 압축 해제
        - bootstrap.min.css, bootstrap.min.js templates/static 에 위치
    12. /templates/board/list.html, detail.html 부트스트랩 적용

        <img src="https://raw.githubusercontent.com/riversoso/basic-springboot-2024/main/images/sp004.png" width="730">


## 6일차
- Spring Boot JPA 프로젝트 개발 계속
    1. (설정) Thymeleaf 레이아웃 사용을 위한 디펜던시 추가
    2. /templates/layout.html Thymeleaf로 레이아웃 템플릿 생성
    3. list.html, detail.html 레이아웃 템플릿 적용
    4. /templates.layout.html에 Bootstrap CDN 적용
    5. /templates/board/list.html에 게시글 등록버튼 추가
    6. /templates/board/create.html 게시글 작성 페이지 작성
    7. /controller/BoardController.java create() GetMapping 메서드 작성
    8. /service/BoardService.java setBoard() 작성
    9. /controller/BoardController.java create() PostMapping 메서드 작성
    10. (문제) 아무내용도 안적어도 저장됨
    11. (설정) build.gradle 입력값 검증 Spring Boot validation
    12. /validation/BoardForm.java 클래스 생성
    13. /controller/BoardController.java에 BoardForm을 전달(Get, PostMapping 둘 다 )
    14. create.html 입력항목 name, id를 th:field로 변경(ex. filed="*{title}")
    15. 댓글등록에도 반영. ReplyForm, ReplyController, detail.html 작업 (12~14 내용과 유사)
    16. detail.html 경고영역 div는 create.html에서 복사해서 가져올 것
    17. (문제) 각 입력창에 공백을 넣었을 때 입력되는 문제 @NotEmpty는 스페이스를 허용 -> @NotBlank로 변경

        <img src="https://raw.githubusercontent.com/riversoso/basic-springboot-2024/main/images/sp005.png" width="730">


    18. /templates/layout 네비게이션바(navbar) 추가
    19. 테스트로 대량 데이터 추가

## 7일차
- Spring Boot JPA 프로젝트 개발 계속
    0. 개념
        ```sql
        -- Oracle 전용(11g 이하는 이 쿼리가 동작안함)
        select b1_0.bno,b1_0.content,b1_0.create_date,b1_0.title 
        from board b1_0 offset 0 -- 0부터 시작해서 페이지 사이즈만큼 증가
        rows fetch first 10 rows only -- 페이지사이즈
        ```

    1. 페이징(중요!)
        - /repository/BoardRepository.java findAll(pageable) 인터페이스 메서드 작성
        - /service/BoardService.java getList(page) 메서드 작성
        - /controller/BoardController.java list() 메서드 수정
        - /templates/board/list.html boardList -> paging 변경
        - /templates/board/list.html 하단 페이징 버튼 추가, thymeleat 기능추가
        - /service/BoardService.java getList() 최신순 역정렬로 변경
        - /templates/board/list.html에 게시글 번호 수정

        <img src="https://raw.githubusercontent.com/riversoso/basic-springboot-2024/main/images/sp006.png" width="730">

    2. /templates/board/list.html td 뱃지태그 추가

    3. H2 -> Oracle로 DB변경
        - build.gradle, Oracle 디펜던시 추가
        - application.properties Oracle 관련 설정 추가, H2 설정 주석처리
        - 재시작

    4. 스프링시큐리티(그 다음 중요!)
        - (설정) build.gradle 스프링 시큐리티 관련 디펜던시 추가
        - (설정) Gradle 재빌드, 서버 실행
        - user / 로그상 UUID (서버 실행 시 마다 변경) 입력
        - /security/SecurityConfig.java 보안설정 파일 생성, 작성 -> 시큐리티를 다시 풀어주는 일

        - /entity/Member.java 생성
        - /repository/MemberRepository.java 인터페이스 생성
        - /service/MemberService.java 생성 setMember() 메서드 작성

## 8일차
- Spring Boot JPA 프로젝트 개발 계속
    1. 스프링 시큐리티 계속
        - /security/SecurityConfig.java에 BCryptPasswordEncoder를 빈으로 작업
        - /validation/MemberForm.java 생성
        - /controller/MemberController.java 생성
        - /entity/Member.java에 regDate 추가
        - /service/MemberService.java regdate() 부분 추가작성
        - /templates/member/register.html 생성
        - /(설정) Member 테이블에 저장된 회원정보 확인
        - /templates/layout.html에 회원가입 링크 추가
        - /controller/MemberController.java Postmapping register에 중복회원가입 방지 추가
        - /security/MemberRole.java enum으로 ROLE_ADMIN, ROLE_USER 생성
        - /entity/Member

    2. 로그인 기능
        - /security/SecurityConfig.java 에 login url 설정
        - /templates/layout.html 로그인 링크 수정
        - /templates/member/login.html 생성
        - /repository/MemberRepository.java find* 메서드 추가
        - /controller/MemberController.java login Get 메서드 작성
        - /service/MemberSecurityService.java - 로그인은 post를 사용하지 않고, Spring Security가 지원하는 UserDetailsService 인터페이스 활용
        - /Security/SecurityConfig.java 계정관리자 빈 추가
        - /templates/layout.html 로그인/로그아웃 토글메뉴 추가

    3. 게시글 작성자 추가
        - /entity/Board.java, /entity/Reply.java 에 작성자 변수(속성) 추가
        - /service/MemberService.java getMember() 메서드
        - (Tip) default Exception으로 예외를 처리하면 메서드 뒤에 항상 throws Exception 을 적어줘야 함.
        - /common/NotFoundException.java 생성 -> throws Exception 쓰는데 반영
        - /service/ReplyService.java setReply() 사용자 추가
        - /controller/ReplyController.java 오류나는 setReply() 파라미터 수정
        - /service/BoardService.java ..
        - /controller/BoardController.java setBoard() 사용자 추가
        - /controller/ 작성하는 get/Post 메서드에 @PreAuthorize 어노테이션 추가
        - /config/SecurityConfig.java @PreAuthorize 동작하도록 설명추가
        - /templates/board/detail.html 답변 textarea 로그인전, 로그인후로 구분

        - /templates/board/list.html table 태그에 작성자 컬럼 추가
        - /templates/board/detail.html 게시글 작성자, 댓글 작성자 표시 추가

    ## 9일차
    - Spring Boot JPA 프로젝트 개발 계속
        - 수정, 삭제
        - 앵커 기능
        - 마크다운 적용, 마크다운 에디터 추가
        - 검색 기능
        - 카테고리 추가(게시판, QnA, 공지사항)
        - 비밀번호 찾기, 비밀번호 변경
        - 조회수 추가

        - 리액트 적용
        - 리액트로 프론트엔드 설정
        - thymeleaf - 리액트로 변경
        - Spring boot RestAPI 작업

        - AWS 라이트세일 가입
        - 서버 접속 프로그램 설정
        - 웹서버 배포
        - 8080 -> 80 서버
        - http -> https 변경