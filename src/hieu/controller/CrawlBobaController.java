package hieu.controller;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.thread.boba.CategoryThread;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CrawlBobaController", urlPatterns = "/crawlBoba")
public class CrawlBobaController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = "admin.jsp";
        try {
            HttpSession session = request.getSession();
            String role = (String) session.getAttribute("ROLE");
            if (role.equals("admin")) {
                String realPath = getServletContext().getRealPath("/");
                Crawler crawler = new Crawler(realPath, GlobalPath.BOBA_CATEGORY_XSL, GlobalPath.BOBA_URL);
                crawler.setFromPart(HTMLPortion.FROM_PART_BOBA_CATEGORY);
                crawler.setToPart(HTMLPortion.TO_PART_BOBA_CATEGORY);
                CategoryThread thread = new CategoryThread(crawler);
                thread.start();
            } else {
                request.setAttribute("UNAUTHORIZED", "You are not admin.");
                url = "error.jsp";
            }
        } catch (Exception ex) {
            Logger.getLogger(CrawlBobaController.class.getName()).log(Level.SEVERE, null, ex);
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
