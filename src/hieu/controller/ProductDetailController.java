package hieu.controller;

import hieu.ResultObject;
import hieu.entity.ProductEntity;
import hieu.entity.RoomEntity;
import hieu.repository.FavouriteRepository;
import hieu.repository.ProductRepository;
import hieu.repository.RoomRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ProductDetailController", urlPatterns = "/detail")
public class ProductDetailController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String DETAIL = "detail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("USERNAME");
            String productHash = request.getParameter("productHash");
            if (!productHash.isEmpty()) {
                ProductRepository proRepo = ProductRepository.getInstance();
                ProductEntity productEntity = proRepo.getProductByHash(productHash);

                FavouriteRepository favorRepo = FavouriteRepository.getInstance();
                if (favorRepo.isExisted(username, productHash)) {
                    request.setAttribute("ADDED", true);
                }

                RoomRepository roomRepo = RoomRepository.getInstance();
                List<RoomEntity> roomEntityList = roomRepo.getRoomByProductHash(productHash);

                Map<String, ResultObject> mapOfProductList = new HashMap<>();
                ResultObject result;
                for (RoomEntity room : roomEntityList) {
                    result = roomRepo.getFirstFiveProductByRoomId(room.getId(), productHash, productEntity.getPrice());
                    mapOfProductList.put(room.getId(), result);
                }

                request.setAttribute("DETAIL", productEntity);
                request.setAttribute("SUGGESTION", mapOfProductList);
                request.setAttribute("ROOMLIST", roomEntityList);
                url = DETAIL;
            } else {
                request.setAttribute("INVALID", "Có lỗi xảy ra, xin kiểm tra lại log server.");
            }
        } catch (Exception ex) {
            request.setAttribute("ERROR", "Lỗi hiển thị chi tiết sản phẩm, kiểm tra log server");
            Logger.getLogger(ProductDetailController.class.getName()).log(Level.SEVERE, null, ex);
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
