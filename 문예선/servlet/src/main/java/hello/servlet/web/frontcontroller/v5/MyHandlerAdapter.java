package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {

    /**
     * 이 어댑터가 넘겨받은 핸들러를 처리할 수 있는지 판단한다.
     */
    boolean supports(Object handler);

    /**
     * 실제 핸들러를 호출하고, 결과를 ModelView 로 맞춰서 반환한다.
     */
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException, IOException;
}
