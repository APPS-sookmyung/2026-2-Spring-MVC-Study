# Spring_MVC_Week1

# 1. 웹과 HTTP

### HTTP

-   웹은 **HTTP 기반의 클라이언트-서버 구조**로 동작.
-   HTTP 메시지를 통해 다양한 데이터를 전송 가능.


# 2. Web Server와 WAS

### Web Server

**정적 리소스**를 제공하는 서버

-   HTML, CSS, JS, 이미지, 영상 등

### WAS

**애플리케이션 로직을 실행**하는 서버

-   동적 HTML
-   HTTP API(JSON)
-   Servlet, JSP, Spring MVC



# 3. Web Server + WAS + DB 구조

역할을 분리함.

-   Web Server → 정적 리소스 처리
-   WAS → 애플리케이션 로직 처리
-   DB → 데이터 저장



# 4. Servlet

## Servlet

HTTP 요청과 응답을 편리하게 처리할 수 있도록 해주는 기술.

``` java
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(
        HttpServletRequest request,
        HttpServletResponse response) {

        // 애플리케이션 로직
    }
}
```

### 주요 특징

-   `/hello`와 같은 URL이 호출되면 해당 Servlet 실행.
-   `HttpServletRequest` → HTTP 요청 정보를 편리하게 사용.
-   `HttpServletResponse` → HTTP 응답 정보를 편리하게 작성.
-   개발자는 HTTP 스펙을 직접 처리하는 복잡한 작업을 줄일 수 있음.



# 5. Servlet Container

**Tomcat처럼 Servlet을 지원하는 WAS**를 Servlet Container라고 함.

### 역할

-   Servlet 객체 생성
-   초기화
-   호출
-   종료
-   생명주기 관리
-   멀티스레드 처리 지원

### Servlet은 Singleton으로 관리

요청이 올 때마다 Servlet 객체를 새로 만드는 것이 아니라 하나의 Servlet
객체를 여러 요청이 공유.
따라서 동시 요청 처리를 위해 멀티스레드를 지원함.


# 6. Thread

### Thread

애플리케이션 코드를 하나씩 순차적으로 실행하는 실행 단위.

-   한 Thread는 한 번에 하나의 코드 라인을 실행함.
-   동시 처리가 필요하면 Thread를 추가로 사용함.



# 7. Thread 생성

여러 요청을 동시에 처리하기 위해 요청마다 Thread를 생성할 수 있음.

하지만 Thread 생성 비용이 크고, Context Switching 비용이 발생. Thread 생성에 제한이 없으면 요청 폭주 시 서버가 다운될 수 있음.

### Thread Pool

요청마다 Thread를 생성하는 문제를 해결하기 위한 방법.

필요한 Thread를 미리 만들어 Thread Pool에 보관하고 필요할 때 꺼내서
사용.

1.  Thread Pool에 Thread를 미리 생성
2.  요청이 들어오면 사용 가능한 Thread를 가져옴
3.  요청 처리
4.  처리가 끝나면 Thread Pool에 반납
5.  사용 가능한 Thread가 없으면 요청을 대기시키거나 거절할 수 있음



# 8. 정적 리소스와 동적 HTML

## 정적 리소스

이미 만들어진 파일을 그대로 제공함.

-   HTML
-   CSS
-   JS
-   이미지
-   영상

## 동적 HTML

서버가 필요한 데이터를 조회한 후 HTML을 동적으로 생성하여 전달.



# 9. HTTP API

HTML 페이지가 아니라 **데이터를 전달하는 방식**.

주로 JSON을 사용함.

### HTTP API의 활용

-   웹 클라이언트 ↔ 서버
-   앱 ↔ 서버
-   서버 ↔ 서버
-   기업 간 데이터 통신



# 10. SSR과 CSR

## SSR (Server Side Rendering)

**서버에서 최종 HTML을 생성하여 브라우저에 전달**

주로 정적인 화면을 만들 때 사용.

## CSR (Client Side Rendering)

**클라이언트(브라우저)에서 JavaScript를 이용해 HTML을 동적으로 생성**

주로 동적인 화면에 사용하며, 필요한 부분을 부분적으로 변경할 수 있음.

