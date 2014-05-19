/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Categoria;
import beans.Vehiculo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import managers.DatabaseManager;
import managers.LoggerManager;

/**
 *
 * @author confalonieri
 */
public class VisualizaDatosServlet extends HttpServlet {

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

        DatabaseManager.openConnection();
        HttpSession session = request.getSession();

        //1. declarar una lista de categoria
        ArrayList<Categoria> categoriaList;

        //2. llamar una funcion que devuelve una lista de categorias
        categoriaList = getCategoriasYVehiculos();
        
        //3. guardo la lista en la session
        session.setAttribute("categoriaList", categoriaList);
        
        DatabaseManager.closeConnection();
        
        request.setAttribute("view", "view.jsp");
        //4. Envio a view.jsp
        request.getRequestDispatcher("/view.jsp").forward(request, response);

        /* response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // TODO output your page here. You may use following sample code.
            out.println("<!DOCTYPE html>");
            out.println("<a href=\"logout\">Logout</a>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet VisualizaDatosServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet VisualizaDatosServlet</h1>");
            if (categoriaList == null) {
                  //imprimo un mensaje de error
                  out.println("Fatal ERROR");
            }
            else if (categoriaList.isEmpty()) {
                //la lista esta vacia
                out.println("<p>El database no tiene ninguna categoria</p>");
            }
            else {
                //3. imprimir la lista de categorias y vehiculos
                for (int i = 0; i < categoriaList.size(); i++) {
                    Categoria categoriaTmp = categoriaList.get(i);
                    out.println("<h2>Tipo Categoria: " + categoriaTmp.getTipo() + " </h2>");

                    //imprimir la lista de vehiculos...
                    for (int j = 0; j < categoriaTmp.getListaVehiculos().size(); j++) {
                        Vehiculo vehiculoTmp = categoriaTmp.getListaVehiculos().get(j);
                        out.println("<p>" + vehiculoTmp.toString() + "</p>");
                    }

                }
            }

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
            DatabaseManager.closeConnection();
        } */
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

    private ArrayList<Categoria> getCategoriasYVehiculos() {

        ArrayList<Categoria> categoriaList = new ArrayList<Categoria>();

        //crear query para recuperar las categorias
        String categoriaSql = "SELECT * FROM categoria";

        //declaro los objetos Java para la query
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            //ejecutar query
            preparedStatement = DatabaseManager.conn.prepareStatement(categoriaSql);
            resultSet = preparedStatement.executeQuery();

            //processar query
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String tipo = resultSet.getString("tipo");
                String descripcion = resultSet.getString("descripcion");
                Categoria categoria = new Categoria(id, tipo, descripcion);
                //por cada categoria, crear la lista de vehiculos
                categoria.crearListaVehiculos(id);
                //añadir la categoria a la lista
                categoriaList.add(categoria);

            }

            preparedStatement.close();
            resultSet.close();

        } catch (SQLException ex) {

            categoriaList = null;
            LoggerManager.getLog().error(ex.toString());

        } finally {
            return categoriaList;
        }
    }

}
