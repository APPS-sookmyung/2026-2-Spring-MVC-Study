package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogTestController {

    // @Slf4j 가 컴파일할 때 아래 한 줄을 대신 만들어 준다
    // private final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        // 이렇게 쓰면 로그를 출력하지 않는 레벨에서도 문자열 더하기가 먼저 실행된다. 쓰지 말 것
        log.trace("String concat log=" + name);

        return "ok";
    }
}
