/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import beans.Categoria;
import beans.Coche;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author confalonieri
 */
public class ClaseServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        //Categoria categoriaCoche1 = new Categoria("deportivo", "corre mucho");
        Categoria categoria1 = new Categoria("deportivo", "corre mucho");
        
        
        Categoria categoria2 = new Categoria("familiar", "no corre mucho");

        Coche coche1 = new Coche("AXddd", "rojo", "bmw", 240, "320");
        Coche coche2 = new Coche("Axxx", "rojo", "audi", 240, "A4");
        categoria1.addVehiculo(coche1);
        categoria1.addVehiculo(coche2);
        
        Coche coche3 = new Coche("AXddd", "rojo", "fiat", 160, "panda");
        Coche coche4 = new Coche("AXddd", "rojo", "seat", 160, "ibiza");
        categoria2.addVehiculo(coche3);
        categoria2.addVehiculo(coche4);
        
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        categorias.add(categoria1);
        categorias.add(categoria2);

       
       
       
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ClaseServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClaseServlet at " + request.getContextPath() + "</h1>");
            for (int i=0;i<categorias.size();i++) {
                Categoria categoria = categorias.get(i);
                out.println("<p>"+categoria.toString()+"</p>");
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
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
