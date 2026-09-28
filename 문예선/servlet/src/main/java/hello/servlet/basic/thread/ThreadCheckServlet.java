package hello.servlet.basic.thread;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 강의 코드가 아닌 추가 확인용 실습.
 * 1) 요청마다 어떤 쓰레드가 실행하는지
 * 2) 서블릿 객체가 하나만 있는지
 * 3) 필드를 공유하면 어떤 문제가 생기는지 확인한다.
 */
@WebServlet(name = "threadCheckServlet", urlPatterns = "/thread-check")
public class ThreadCheckServlet extends HttpServlet {

    private int count = 0; // 모든 요청이 함께 읽고 쓰는 필드 (문제를 보여주기 위한 코드)

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String threadName = Thread.currentThread().getName();
        int servletId = System.identityHashCode(this);

        int current = count;      // 1. 필드 값을 읽는다
        sleep(100);               // 2. 다른 요청이 끼어들 시간을 일부러 만든다
        count = current + 1;      // 3. 읽었던 값 + 1 을 필드에 쓴다

        System.out.println("thread=" + threadName + ", servletId=" + servletId + ", count=" + count);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("thread=" + threadName + ", servletId=" + servletId + ", count=" + count);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
