package hieu.controller;

import hieu.entity.FavouriteEntity;
import hieu.repository.FavouriteRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddFavouriteController", urlPatterns = "/addFavourite")
public class AddFavouriteController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String DETAIL = "detail";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();

            String productHash = request.getParameter("productHash");
            String username = (String) session.getAttribute("USERNAME");

            FavouriteRepository repository = FavouriteRepository.getInstance();

            if (!repository.isExisted(username, productHash)) {
                FavouriteEntity favouriteEntity = new FavouriteEntity(username, productHash);
                repository.insert(favouriteEntity);
            } else {
                request.setAttribute("ADDED", true);
            }

            url = DETAIL;
        } catch (Exception ex) {
            request.setAttribute("ERROR", "Lỗi thêm mục yêu thích, kiểm tra log server");
            Logger.getLogger(AddFavouriteController.class.getName()).log(Level.SEVERE, null, ex);
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
