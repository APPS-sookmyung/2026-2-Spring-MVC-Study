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

    private final java.util.concurrent.atomic.AtomicInteger count = new java.util.concurrent.atomic.AtomicInteger();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String threadName = Thread.currentThread().getName();
        int servletId = System.identityHashCode(this);

        sleep(100);
        int now = count.incrementAndGet(); // 읽기, 더하기, 쓰기를 한 번에

        System.out.println("thread=" + threadName + ", servletId=" + servletId + ", count=" + now);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("thread=" + threadName + ", servletId=" + servletId + ", count=" + now);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
