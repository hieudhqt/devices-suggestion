package hieu.controller;

import hieu.entity.AccountEntity;
import hieu.repository.AccountRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String INDEX = "index.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String USER = "init";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            AccountRepository repository = AccountRepository.getInstance();
            AccountEntity account = repository.login(username, password);
            if (account != null) {
                String role = account.getRole();
                String name = account.getName();
                if (role != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("USERNAME", username);
                    session.setAttribute("NAME", name);
                    session.setAttribute("ROLE", role);
                    switch (role) {
                        case "admin":
                            url = ADMIN;
                            break;
                        case "user":
                            url = USER;
                            break;
                        default:
                            request.setAttribute("UNAUTHORIZED", "QUYỀN CỦA BẠN KHÔNG ĐƯỢC HỖ TRỢ");
                    }
                }
            } else {
                request.setAttribute("UNAUTHENTICATED", "SAI TÊN TÀI KHOẢN/MẬT KHẨU");
                url = INDEX;
            }
        } catch (Exception ex) {
            request.setAttribute("ERROR", "ĐĂNG NHẬP THẤT BẠI");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
