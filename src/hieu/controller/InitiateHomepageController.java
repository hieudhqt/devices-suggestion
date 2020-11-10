package hieu.controller;

import hieu.entity.CategoryEntity;
import hieu.entity.ProductEntity;
import hieu.repository.CategoryRepository;
import hieu.repository.FavouriteRepository;
import hieu.repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "InitiateHomepageController", urlPatterns = "/init")
public class InitiateHomepageController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String HOMEPAGE = "homepage.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            CategoryRepository cateRepo = CategoryRepository.getInstance();
            List<CategoryEntity> categoryEntities = cateRepo.getAll();

            FavouriteRepository favoRepo = FavouriteRepository.getInstance();
            List<ProductEntity> productEntityList = favoRepo.getMostFavouriteProducts();

            ProductRepository proRepo = ProductRepository.getInstance();
            float maxPrice = proRepo.getMaxPrice();
            float minPrice = proRepo.getMinPrice();

            request.setAttribute("CATELIST", categoryEntities);
            request.setAttribute("PRODUCTLIST", productEntityList);
            request.setAttribute("MAXPRICE", maxPrice);
            request.setAttribute("MINPRICE", minPrice);

            url = HOMEPAGE;
        } catch (Exception ex) {
            request.setAttribute("ERROR", "Khởi tạo trang chủ lỗi, kiểm tra log server");
            Logger.getLogger(InitiateHomepageController.class.getName()).log(Level.SEVERE, null, ex);
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
