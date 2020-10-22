package hieu.controller;

import hieu.constant.GlobalPath;
import hieu.constant.HTMLPortion;
import hieu.crawler.Crawler;
import hieu.thread.techbox.CategoryThread;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CrawlController", urlPatterns = "/crawl")
public class CrawlController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            String realPath = getServletContext().getRealPath("/");
            Crawler crawler = new Crawler(realPath, GlobalPath.TECHBOX_CATEGORY_XSL, GlobalPath.TECHBOX_URL);
            crawler.setFromPart(HTMLPortion.FROM_PART_TECHBOX_CATEGORY);
            crawler.setToPart(HTMLPortion.TO_PART_TECHBOX_CATEGORY);
            CategoryThread categoryThread = new CategoryThread(crawler);
            categoryThread.start();
        } catch (Exception ex) {
            Logger.getLogger(CrawlController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.getRequestDispatcher("").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
