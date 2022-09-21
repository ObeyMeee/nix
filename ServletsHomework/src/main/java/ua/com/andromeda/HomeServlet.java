package ua.com.andromeda;

import ua.com.andromeda.model.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final List<UserInfo> LIST = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ipAddress = req.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = req.getLocalAddr();
        }
        UserInfo userInfo = new UserInfo(ipAddress, req.getHeader("User-Agent"), LocalDateTime.now());
        LIST.add(userInfo);
        req.setAttribute("list", LIST);
        req.getRequestDispatcher("ip-list.jsp").forward(req, resp);
    }
}
