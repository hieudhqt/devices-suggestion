package hieu.controller;

import hieu.constant.GlobalPath;
import hieu.entity.ProductEntity;
import hieu.jaxb.report.Product;
import hieu.jaxb.report.Products;
import hieu.repository.FavouriteRepository;
import hieu.repository.RoomRepository;
import hieu.util.JAXBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ReportController", urlPatterns = "/report")
public class ReportController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SHOW = "showFavourite?pageNumber=1";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("USERNAME");
            String name = (String) session.getAttribute("NAME");
            String role = (String) session.getAttribute("ROLE");

            if (role != null) {
                if (role.equals("user")) {
                    FavouriteRepository favoRepo = FavouriteRepository.getInstance();
                    List<ProductEntity> productEntityList = favoRepo.getFavouriteProductsForReporting(username);

                    if (!productEntityList.isEmpty()) {

                        RoomRepository roomRepo = RoomRepository.getInstance();

                        GregorianCalendar gregorianCalendar = new GregorianCalendar();
                        XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

                        Products products = new Products();
                        products.setName(name);
                        products.setTime(xmlCalendar);

                        for (ProductEntity productEntity : productEntityList) {
                            String hash = productEntity.getHash();

                            Product product = new Product();
                            product.setName(productEntity.getName());
                            product.setCategory(productEntity.getCategoryByCategoryHash().getName());
                            product.setPrice(productEntity.getPrice().toString());
                            product.setWarranty(productEntity.getWarranty());
                            product.getRoom().addAll(roomRepo.getRoomNameByProductHash(hash));

                            products.getProduct().add(product);
                        }

                        ByteArrayOutputStream outputStream = JAXBUtil.marshall(products, Products.class);

                        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

                        String realPath = getServletContext().getRealPath("/");
                        String xslFile = GlobalPath.REPORT_PDF_XSL;

                        JAXBUtil.convertToPDF(inputStream, realPath, xslFile, username);

                        url = SHOW;
                    }
                }
            }

        } catch (Exception ex) {
            request.setAttribute("ERROR", "Lỗi tạo report, kiểm tra log server");
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
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
