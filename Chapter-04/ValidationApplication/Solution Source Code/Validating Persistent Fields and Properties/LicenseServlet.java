package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import packt.LicenseBean;
import packt.LicenseBeanFacade;

public class LicenseServlet extends HttpServlet {

    @EJB
    LicenseBeanFacade licenseBeanFacade;

    LicenseBean license;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LicenseServlet</title>");
            out.println("</head>");
            out.println("<body>");
            license = new LicenseBean();
            license.setName("Pax Maxwell");
            Calendar calendar = Calendar.getInstance();
            calendar.set(1981, 4, 18);
            license.setDateOfBirth(calendar.getTime());
            license.setMonthsToExpire(50);
            license.setResident(true);
            license.setRestrictions("CT6");

            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set constraintViolations = validator.validate(license);
            Iterator iter = constraintViolations.iterator();
            if (iter.hasNext()) {
                while (iter.hasNext()) {
                    ConstraintViolation constraintViolation = (ConstraintViolation) iter.next();
                    out.println("<h3>Message: " + constraintViolation.getMessage());
                    out.println(" however value given was: " + constraintViolation.getInvalidValue() + "</h3>");
                }
            } else {
                licenseBeanFacade.create(license);
                out.println("<h1>Name: " + license.getName() + " - License ID: "
                        + license.getId() + "</h1>");

            }

            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }

    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
