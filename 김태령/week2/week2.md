#Spring_MVC_Week2

## 1. 서블릿

- 서블릿: HTTP 요청을 받아 처리하고 HTTP 응답을 보내는 자바 프로그램.
- 스프링 부트는 **내장 톰캣**을 제공 → 별도의 톰캣 설치 없이 서블릿 실행 가능.
- `@ServletComponentScan` → `@WebServlet`이 붙은 서블릿 자동 등록.
- `@WebServlet(name, urlPatterns)` → 서블릿 이름과 URL 매핑.
- 매핑된 URL 요청 → 서블릿 컨테이너가 `service()` 실행.

## 2. HttpServletRequest

### 역할

- HTTP 요청 메시지를 개발자가 직접 **파싱**하지 않아도 되도록 서블릿이 파싱해서 `HttpServletRequest` 객체에 담아 제공.
- HTTP 요청 메시지의 정보를 편리하게 조회할 수 있음.

## 3. GET - 쿼리 파라미터

### 형식

`/request-param?username=hello&age=20`

- 메시지 Body 없이 **URL의 쿼리 파라미터**로 전달.
- 검색, 필터, 페이징 등에 사용.
- `?`로 시작하고 추가 파라미터는 `&`로 구분.

### 파라미터 조회

- `request.getParameter(paramName)`
  → 단일 파라미터 조회. key에 해당하는 값을 조회.
- `request.getParameterNames()`
  → 파라미터 이름 전체 조회 (모든 요청 파라미터들을 다 꺼낼 수 있음.)
- `request.getParameterValues("username")`
  → 같은 이름의 여러 파라미터 조회.

### 같은 이름의 파라미터

`?username=hello&username=kim`

- `getParameter()` → 하나의 값만 있을 때 사용.
- `getParameterValues()` → 같은 이름의 값이 여러 개일 때 사용.
- 중복된 파라미터에 `getParameter()` 사용 → **첫 번째 값 반환**.

## 4. POST - HTML Form

- 메시지 **Body**에 쿼리 파라미터 형식으로 전달.
- 주로 회원가입, 상품 주문 등에 사용.

`Content-Type: application/x-www-form-urlencoded`

`username=hello&age=20`

### 특징

- GET → URL의 쿼리 파라미터.
- POST Form → Body의 `application/x-www-form-urlencoded`
- 서버 입장에서는 둘의 형식이 같으므로 `request.getParameter()`로 **둘 다 조회 가능**.


## 5. HTTP API - Message Body

- HTTP 메시지 Body에 데이터를 직접 담아서 전달.
- 주로 `POST`, `PUT`, `PATCH`.
- `TEXT`, `JSON`, `XML` 사용.
- 주로 **JSON** 사용.

### 단순 텍스트

- `request.getInputStream()` → HTTP Body를 직접 읽음.
- `InputStream`은 **byte 코드**를 반환.
- byte를 사람이 읽을 수 있는 `String`으로 변환하려면 **Charset 지정 필요**.
- 여기서는 `UTF-8` 사용.

## 6. HTTP API - JSON

### JSON → Java 객체

- JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 `ObjectMapper` 사용.
- `objectMapper.readValue(messageBody, HelloData.class)`.
- HelloData.class가 JSON 형식.
- JSON 문자열을 Java 객체로 변환.

### Java 객체 → JSON

- `objectMapper.writeValueAsString(data)`
- Java 객체를 JSON 문자열로 변환.

### Lombok

- `@Getter`, `@Setter`
  → getter/setter 자동 생성.

> Spring MVC를 사용하면 Jackson의 `ObjectMapper`를 기본 제공.

## 7. HTTP 응답 데이터

### 단순 텍스트

- `response.getWriter().write("ok")`

### HTML

- `Content-Type: text/html`
- HTML 응답을 반환할 때 `text/html` 지정.

### JSON

- `Content-Type: application/json`
- `ObjectMapper.writeValueAsString()`으로 객체를 JSON 문자열로 변환.

> `application/json`은 스펙상 UTF-8을 사용하므로 `charset=utf-8`을 추가하지 않음.

