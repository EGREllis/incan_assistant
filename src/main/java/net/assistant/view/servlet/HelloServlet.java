package net.assistant.view.servlet;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class HelloServlet extends HttpServlet {
    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name", this.message);
        request.setAttribute("message", this.message);
        request.setAttribute("time", new Date());
        getServletContext().getRequestDispatcher("welcome.jsp").forward(request, response);
    }
}
