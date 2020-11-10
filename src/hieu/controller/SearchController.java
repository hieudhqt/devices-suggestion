package hieu.controller;

import hieu.PageResult;
import hieu.entity.ProductEntity;
import hieu.repository.CategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SearchController", urlPatterns = "/search")
public class SearchController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String HOMEPAGE = "init";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            String categoryHash = request.getParameter("categoryHash");
            String fromPriceString = request.getParameter("fromPrice");
            String toPriceString = request.getParameter("toPrice");
            String pageNumberString = request.getParameter("pageNumber");

            float fromPrice = convertStringToFloat(fromPriceString);
            float toPrice = convertStringToFloat(toPriceString);
            int pageNumber = convertStringToInt(pageNumberString);

            CategoryRepository repository = CategoryRepository.getInstance();
            PageResult pageResult = repository.getProductEntitiesByCategoryHash(categoryHash, fromPrice, toPrice, pageNumber);
            List<ProductEntity> listProduct = pageResult.getElements();
            request.setAttribute("INFO", listProduct);
            request.setAttribute("SELECTED", categoryHash);
            request.setAttribute("PREVIOUS", pageResult.hasPrevious());
            request.setAttribute("NEXT", pageResult.hasMore());
            if (listProduct.isEmpty()) pageNumber = 0;
            request.setAttribute("CURRENTPAGE", pageNumber);
            request.setAttribute("MAXRESULT", pageResult.getTotalElements());
            request.setAttribute("COUNTNO", pageResult.calculateOffset());

            url = HOMEPAGE;
        } catch (Exception ex) {
            request.setAttribute("ERROR", "Lỗi tìm kiếm sản phẩm, kiểm tra log server");
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private int convertStringToInt(String input) {
        int output;
        try {
            output = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            output = 0;
        }
        return output;
    }

    private float convertStringToFloat(String input) {
        float output;
        try {
            output = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            output = 0;
        }
        return output;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
