# 6. 스프링 MVC - 기본 기능 (요약)

---

## 1. 프로젝트 생성
- **사이트**: [start.spring.io](https://start.spring.io)
- **프로젝트 설정**
  - Project: Gradle Project
  - Language: Java (11)
  - Spring Boot: 2.4.x
  - Group / Artifact: `hello` / `springmvc`
  - **Packaging: Jar (주의!)**
    - JSP를 사용하지 않고 내장 서버(톰캣 등) 사용에 최적화되어 있음
    - `webapp` 경로를 사용하지 않으며, 정적 리소스는 `/resources/static/`에 위치
  - **Dependencies**: `Spring Web`, `Thymeleaf`, `Lombok`
- **Welcome 페이지**: `/resources/static/index.html` 파일을 두면 자동 인식

---

## 2. 로깅 간단히 알아보기
운영 시스템에서는 `System.out.println()` 대신 별도의 로깅 라이브러리를 사용해야 합니다.

- **라이브러리 구조**
  - **SLF4J**: 로깅 라이브러리들을 통합해서 제공하는 인터페이스
  - **Logback**: 실제 구현체 (스프링 부트 기본 제공)
- **로그 선언**
  - `private final Logger log = LoggerFactory.getLogger(getClass());`
  - `@Slf4j` (롬복 애노테이션 사용 시 자동 생성)
- **로그 레벨 (낮음 → 높음)**
  - `TRACE` > `DEBUG` > `INFO` > `WARN` > `ERROR`
  - 개발 서버는 `debug`, 운영 서버는 `info`로 주로 설정 (`application.properties`에서 설정 가능)
- **올바른 로그 사용법**
  - **X (잘못된 예)**: `log.debug("data=" + data)` → 로그 출력 레벨이 `info`여도 문자 더하기 연산이 먼저 발생해 리소스 낭비
  - **O (올바른 예)**: `log.debug("data={}", data)` → 출력 레벨이 아니면 파라미터만 넘기고 연산하지 않음
- **로그 사용 장점**
  - 쓰레드 정보, 클래스명 등 부가 정보 확인 및 출력 포맷 조정 가능
  - 상황(개발/운영)에 따른 로그 레벨 조절 가능
  - 콘솔 외 파일, 네트워크 등으로 별도 저장 가능 (일별, 용량별 분할 가능)
  - `System.out`보다 성능이 우수함 (내부 버퍼링, 멀티 쓰레드 등)

---

## 3. 요청 매핑
- **`@RestController` vs `@Controller`**
  - `@Controller`: 반환 값이 `String`이면 **뷰 이름**으로 인식하여 뷰를 찾고 렌더링함
  - `@RestController`: 반환 값으로 뷰를 찾지 않고, **HTTP 메시지 바디에 바로 입력**함 (`@ResponseBody` 포함)

### 주요 매핑 방식
- **기본 매핑 (`@RequestMapping`)**
  - `@RequestMapping("/hello-basic")`: 배열 형태로 다중 설정 가능 (`{"/hello-basic", "/hello-go"}`)
  - **주의 (스프링 부트 3.0 이후)**: `/hello-basic`과 `/hello-basic/`는 서로 다른 URL로 구분됨 (3.0 이전에는 동일하게 처리)
- **HTTP 메서드 매핑**
  - `@RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)`
  - 허용하지 않은 메서드로 요청 시 **HTTP 405 (Method Not Allowed)** 반환
  - **축약 애노테이션 (권장)**: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping`
- **PathVariable (경로 변수) 사용**
  - URL 경로를 템플릿화하여 식별자를 조회 (`@GetMapping("/mapping/{userId}")`)
  - 변수명이 같으면 생략 가능: `@PathVariable("userId") String userId` → `@PathVariable String userId`
  - 다중 사용 가능: `@GetMapping("/mapping/users/{userId}/orders/{orderId}")`
- **특정 파라미터 조건 매핑**
  - `@GetMapping(value = "/mapping-param", params = "mode=debug")`
  - 특정 파라미터가 있거나 없는 조건을 추가 (잘 사용하지 않음)
- **특정 헤더 조건 매핑**
  - `@GetMapping(value = "/mapping-header", headers = "mode=debug")`
  - 파라미터 매핑과 유사하나 HTTP 헤더 값을 기준으로 매핑
- **미디어 타입 조건 매핑**
  - **`consumes` (요청의 `Content-Type` 헤더 기반)**
    - 예: `@PostMapping(value = "/mapping-consume", consumes = "application/json")`
    - 불일치 시 **HTTP 415 (Unsupported Media Type)** 반환
  - **`produces` (요청의 `Accept` 헤더 기반)**
    - 예: `@PostMapping(value = "/mapping-produce", produces = "text/html")`
    - 불일치 시 **HTTP 406 (Not Acceptable)** 반환

---

## 4. 요청 매핑 - API 예시
회원 관리를 HTTP API로 설계할 때의 URL 매핑 예시입니다.

### 회원 관리 API 명세
- **회원 목록 조회**: `GET /users`
- **회원 등록**: `POST /users`
- **회원 조회**: `GET /users/{userId}`
- **회원 수정**: `PATCH /users/{userId}`
- **회원 삭제**: `DELETE /users/{userId}`

### 컨트롤러 구현 (`MappingClassController`)
- 클래스 레벨에 `@RequestMapping("/mapping/users")`를 선언하면 메서드 레벨의 경로와 조합되어 동작합니다.

```java
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    @GetMapping // GET /mapping/users
    public String users() {
        return "get users";
    }

    @PostMapping // POST /mapping/users
    public String addUser() {
        return "post user";
    }

    @GetMapping("/{userId}") // GET /mapping/users/{userId}
    public String findUser(@PathVariable String userId) {
        return "get userId=" + userId;
    }

    @PatchMapping("/{userId}") // PATCH /mapping/users/{userId}
    public String updateUser(@PathVariable String userId) {
        return "update userId=" + userId;
    }

    @DeleteMapping("/{userId}") // DELETE /mapping/users/{userId}
    public String deleteUser(@PathVariable String userId) {
        return "delete userId=" + userId;
    }
}```