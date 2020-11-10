package hieu.controller;

import hieu.PageResult;
import hieu.entity.ProductEntity;
import hieu.repository.FavouriteRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ShowFavouriteController", urlPatterns = "/showFavourite")
public class ShowFavouriteController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("USERNAME");
            String pageNumberString = request.getParameter("pageNumber");
            int pageNumber;
            try {
                pageNumber = Integer.parseInt(pageNumberString);
            } catch (NumberFormatException e) {
                pageNumber = 0;
            }
            FavouriteRepository repository = FavouriteRepository.getInstance();
            PageResult pageResult = repository.getFavouriteProductByUsername(username, pageNumber);
            List<ProductEntity> listProduct = pageResult.getElements();
            request.setAttribute("INFO", listProduct);
            request.setAttribute("PREVIOUS", pageResult.hasPrevious());
            request.setAttribute("NEXT", pageResult.hasMore());
            if (listProduct.isEmpty()) pageNumber = 0;
            request.setAttribute("CURRENTPAGE", pageNumber);
            request.setAttribute("COUNTNO", pageResult.calculateOffset());
        } catch (Exception ex) {
            Logger.getLogger(ShowFavouriteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.getRequestDispatcher("favourite.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
