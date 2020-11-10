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

@WebServlet(name = "DeleteFavouriteController", urlPatterns = "/deleteFavourite")
public class DeleteFavouriteController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SHOW = "showFavourite?pageNumber=";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("USERNAME");
            String productHash = request.getParameter("productHash");
            String pageNumberString = request.getParameter("pageNumber");
            int pageNumber;
            try {
                pageNumber = Integer.parseInt(pageNumberString);
            } catch (NumberFormatException e) {
                pageNumber = 1;
            }

            FavouriteRepository repository = FavouriteRepository.getInstance();
            FavouriteEntity favouriteEntity = new FavouriteEntity(username, productHash);
            if (repository.delete(favouriteEntity)) {
                url = SHOW + pageNumber;
            } else {
                request.setAttribute("ERROR", "Có lỗi xảy ra trong quá trình xoá. Vui lòng kiểm tra log server");
            }
        } catch (Exception ex) {
            request.setAttribute("ERROR", "Lỗi xoá mục yêu thích, kiểm tra log server");
            Logger.getLogger(DeleteFavouriteController.class.getName()).log(Level.SEVERE, null, ex);
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
