package vn.iotstar.techstore.controllers;

import java.io.IOException;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ApplicationErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response, Model model)
            throws IOException {
        Object statusAttribute = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusAttribute == null) {
            return "redirect:/";
        }
        int status = statusAttribute instanceof Integer code ? code : HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        if (status < 400) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter();
        model.addAttribute("status", status);
        return "web/error";
    }
}
