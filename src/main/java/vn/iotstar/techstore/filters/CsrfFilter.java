package vn.iotstar.techstore.filters;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

public class CsrfFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String token = (String) session.getAttribute("csrfToken");
        if (token == null) {
            token = UUID.randomUUID().toString();
            session.setAttribute("csrfToken", token);
        }

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String submitted = request.getParameter("csrfToken");
            if (submitted == null) {
                submitted = request.getHeader("X-CSRF-TOKEN");
            }
            if (!token.equals(submitted)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token không hợp lệ.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
