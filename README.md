# basic-springboot-2024
 Java 빅데이터 개발자 과정 Spring Boot 학습 리포지토리

## 1일차
- Spring Boot 개요

- Spring Boot 개발환경 설정
    - Java JDK 확인 > 17버전 이상
	-https://jdk.java.net/archive/
	-시스템 속성>고급>환경변수 중 JAVA_HOME 설정

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
    - Node.js
    - React setting