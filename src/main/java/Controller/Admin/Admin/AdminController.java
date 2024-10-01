/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin.Admin;

import DAO.AdminDAO;
import Model.Admin;
import Utils.Validation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author HP
 */
@WebServlet(name = "AdminController", urlPatterns = {"/managerController"})
public class AdminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("adminLogin") == null  || (session.getAttribute("adminLogin") != null && Integer.parseInt(session.getAttribute("adminRole") + "") != 1)) {
            response.sendRedirect("LoginController?error=Your account can not login here");
            return;
        }
        String action = request.getParameter("action");
        action = action != null ? action : "";
        AdminDAO adminDao = new AdminDAO();
        switch (action) {
            case "add":
                request.getRequestDispatcher("./admin/admin/add.jsp").forward(request, response);
                break;
            case "edit":
                try {
                int idAdmin = Integer.parseInt(request.getParameter("adminID"));
                Admin currentAdmin = adminDao.getAdmin(idAdmin);
                if (currentAdmin != null) {
                    request.setAttribute("currentAdmin", currentAdmin);
                    request.getRequestDispatcher("./admin/admin/edit.jsp").forward(request, response);
                } else {
                    response.sendRedirect("managerController?error=Can not found this account");
                }
            } catch (Exception e) {
                response.sendRedirect("managerController?error=Admin id is not valid");
            }
            break;
            case "update-profile":

                break;
            case "delete":
                try {
                int idAdmin = Integer.parseInt(request.getParameter("adminID"));
                Admin currentAdmin = adminDao.getAdmin(idAdmin);
                if (currentAdmin != null) {
                    int result = adminDao.deleteAdmin(idAdmin);
                    if (result > 0) {
                        response.sendRedirect("managerController?success=Delete account successfully");
                    } else {
                        response.sendRedirect("managerController?error=Delete account fail");
                    }
                } else {
                    response.sendRedirect("managerController?error=Can not found this account");
                }
            } catch (Exception e) {
                response.sendRedirect("managerController?error=Admin id is not valid");
            }
            break;
            default:
                List<Admin> admins = adminDao.getAllAdmin();
                request.setAttribute("admins", admins);
                request.getRequestDispatcher("./admin/admin/list.jsp").forward(request, response);

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action != null ? action : "";
        switch (action) {
            case "add":
                this.addAdmin(request, response);
                break;
            case "edit":
                this.editAdmin(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    private void editAdmin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String idAdmin = request.getParameter("idAdmin").trim();
            String name = request.getParameter("name").trim();
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            String phone = request.getParameter("phone").trim();
            String statusStr = request.getParameter("status").trim();
            int idAdminNumber;
            try {
                idAdminNumber = Integer.parseInt(idAdmin);
            } catch (NumberFormatException e) {
                response.sendRedirect("managerController?error=Admin id is not valid");
                return;
            }
            if (name == null || email == null || phone == null || statusStr == null
                    || name.isEmpty() || email.isEmpty() || phone.isEmpty() || statusStr.isEmpty()) {
                response.sendRedirect("managerController?action=edit&adminID=" + idAdmin + "&error=Please fill all field");
                return;
            }

            if (password.length() == 0) {
                password = request.getParameter("oldPassword");
            }
            int status;

            try {
                status = Integer.parseInt(statusStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("managerController=edit&adminID=" + idAdmin + "&error=Invalid status");
                return;
            }
            Validation validate = new Validation();
            if (!validate.isValidEmail(email)) {
                response.sendRedirect("managerController?action=edit&adminID=" + idAdmin + "&error=Invalid email");
                return;
            }

            if (!validate.isValidPhoneNumber(phone)) {
                response.sendRedirect("managerController?action=edit&adminID=" + idAdmin + "&error=Invalid phone");
                return;
            }

            if (!(password.length() > 7) || password.contains(" ")) {
                response.sendRedirect("managerController?action=edit&adminID=" + idAdmin + "&error=Password must be from 8 character and no space");
                return;
            }

            AdminDAO adminDao = new AdminDAO();
            if (adminDao.isEmailExists(email, idAdminNumber)) {
                response.sendRedirect("managerController?action=edit&adminID=" + idAdmin + "&error=Email exists");
                return;
            }
            if (adminDao.isPhoneExists(phone, idAdminNumber)) {
                response.sendRedirect("managerController?action=edit&adminID=" + idAdmin + "&error=Ehone exists");
                return;
            }
            Admin admin = new Admin(idAdminNumber, name, email, phone, password, status);
            int result = adminDao.updateAdmin(admin);
            if (result > 0) {
                response.sendRedirect("managerController?&success=Update successfully");
            } else {
                response.sendRedirect("managerController?&error=Update fail");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name").trim();
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            String phone = request.getParameter("phone").trim();
            String statusStr = request.getParameter("status").trim();

            if (name == null || email == null || phone == null || statusStr == null
                    || name.isEmpty() || email.isEmpty() || phone.isEmpty() || statusStr.isEmpty()) {
                response.sendRedirect("managerController?action=add&error=Please fill all field");
                return;
            }

            int status;
            try {
                status = Integer.parseInt(statusStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("managerController?action=add&error=Invalid status");
                return;
            }
            Validation validate = new Validation();
            if (!validate.isValidEmail(email)) {
                response.sendRedirect("managerController?action=add&error=Invalid email");
                return;
            }

            if (!validate.isValidPhoneNumber(phone)) {
                response.sendRedirect("managerController?action=add&error=Invalid phone");
                return;
            }

            if (!(password.length() > 7) || password.contains(" ")) {
                response.sendRedirect("managerController?action=add&error=Password must be from 8 character and no space");
                return;
            }

            AdminDAO adminDao = new AdminDAO();
            if (adminDao.isEmailExists(email, 0)) {
                response.sendRedirect("managerController?action=add&error=Email exists");
                return;
            }
            if (adminDao.isPhoneExists(phone, 0)) {
                response.sendRedirect("managerController?action=add&error=Ehone exists");
                return;
            }
            Admin admin = new Admin(0, name, email, phone, password, status);
            int result = adminDao.addAdmin(admin);
            if (result > 0) {
                response.sendRedirect("managerController?&success=Add new successfully");
            } else {
                response.sendRedirect("managerController?&error=Add new fail");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
