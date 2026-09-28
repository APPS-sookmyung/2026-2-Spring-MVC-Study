package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {

    /**
     * @param paramMap 요청 파라미터
     * @param model    프론트 컨트롤러가 만들어서 넘겨주는 모델
     * @return viewName 뷰의 논리 이름
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
