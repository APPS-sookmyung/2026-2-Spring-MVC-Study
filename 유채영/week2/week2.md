## 1. HttpServletRequest 개요 & 기본 사용법

### 1) HttpServletRequest의 역할
- 개발자가 복잡한 HTTP 요청 메시지를 직접 파싱하지 않도록 WAS가 메시지를 대신 파싱하여 객체에 담아 제공
- **주요 조회 정보**
  - **Start-Line**: HTTP 메서드, URL, URI, 쿼리 스트링, 프로토콜, HTTPS 사용 여부 등
  - **Header**: Host, 언어(Accept-Language), Cookie, Content-Type, Content-Length 등
  - **Body**: Form 데이터 및 API Message Body
- **부가 기능**
  - **임시 저장소**: 해당 HTTP 요청의 생명주기 동안 유지되는 저장소 (`setAttribute`, `getAttribute`)
  - **세션 관리**: 세션 생성 및 조회 (`getSession`)

---

## 2. HTTP 요청 데이터 전달 방식 3가지

### 1) GET - 쿼리 파라미터
- 메시지 바디 없이 **URL의 쿼리 파라미터(`?key=value&...`)**를 통해 데이터 전달
- 주로 검색, 필터, 페이징 등에 사용
- 조회 메서드:
  - `request.getParameter(name)`: 단일 파라미터 조회
  - `request.getParameterValues(name)`: 동일한 키의 복수 파라미터 조회 (단일 조회 시 첫 번째 값만 반환)
  - `request.getParameterNames()`, `request.getParameterMap()`

### 2) POST - HTML Form
- `Content-Type: application/x-www-form-urlencoded`
- 메시지 바디에 쿼리 파라미터 형식(`username=kim&age=20`)으로 전달
- 주로 회원가입, 상품 주문 등에 사용
- **핵심**: 데이터가 바디에 담겨 올 뿐 형태가 쿼리 파라미터와 동일하므로 서버에서는 GET 방식과 동일하게 `request.getParameter(...)`로 조회 가능

### 3) API 메시지 바디 (HTTP Message Body 직접 전달)
- HTTP API(REST API)에서 주로 사용하며 POST, PUT, PATCH에 적용
- **단순 텍스트**
  - `request.getInputStream()`을 통해 바디의 바이트 코드를 읽은 뒤 인코딩(`UTF-8`)을 지정해 문자열로 변환
- **JSON 형식**
  - `Content-Type: application/json`
  - 메시지 바디의 JSON 텍스트를 읽은 후 JSON 파싱 라이브러리(Spring MVC 기본 내장인 **Jackson의 `ObjectMapper`**)를 사용해 자바 객체로 변환

---

## 3. HttpServletResponse 기본 사용법

### 1) HttpServletResponse의 역할
- HTTP 응답 메시지 생성 (HTTP 상태 코드 지정, 응답 헤더 생성, 응답 바디 생성)
- 개발자 편의 기능 제공 (Content-Type 설정, 쿠키 발급, 리다이렉트 처리)

### 2) 주요 편의 기능
- **상태 코드**: `response.setStatus(HttpServletResponse.SC_OK)` (200, 302, 404 등 지정)
- **Content 설정**: `setContentType("text/plain")`, `setCharacterEncoding("utf-8")` 지원 (생략 시 `Content-Length`는 WAS가 자동 계산)
- **쿠키(Cookie)**: `Cookie` 객체를 생성하여 유효기간(Max-Age) 설정 후 `response.addCookie(cookie)`로 편하게 전송
- **리다이렉트(Redirect)**: `response.sendRedirect(경로)` 호출 시 302 상태 코드와 Location 헤더 자동 지정

---

## 4. HTTP 응답 데이터 제공 방식 3가지

### 1) 단순 텍스트 응답
- `response.getWriter().write(...)`를 사용하여 단순 문자열 반환

### 2) HTML 응답
- `Content-Type: text/html` 및 `charset=utf-8` 지정
- `response.getWriter()` 스트림을 통해 `<html><body>...</body></html>` 태그를 직접 출력하여 브라우저에 화면 렌더링

### 3) API - JSON 응답
- `Content-Type: application/json` 지정
- 자바 객체를 Jackson의 `ObjectMapper`(`writeValueAsString(...)`)를 통해 JSON 형식의 문자열로 변환한 뒤 응답 바디에 출력
- **참고**: HTTP 스펙상 `application/json`은 기본 인코딩이 UTF-8로 정의되어 있어 별도의 `charset=utf-8` 파라미터를 추가하지 않는 것이 권장됨