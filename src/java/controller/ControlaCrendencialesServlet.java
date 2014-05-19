/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import managers.LoggerManager;

/**
 *
 * @author confalonieri
 */
public class ControlaCrendencialesServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        String prefix = getServletContext().getRealPath("/");
        ///Users/confalonieri/Dropbox/Roberto/stucom/DAW/tools-projects/NetBeansProjects/Practica34/web
        LoggerManager.prefix = prefix;

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
        //processRequest(request, response);

        HttpSession session = request.getSession();
        session.invalidate();
        String path = "/login.jsp";
        request.getRequestDispatcher(path).forward(request, response);

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
        //processRequest(request, response);

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        String path = null;
        String tipoUsuario;

        if (LoggerManager.DEBUG) {
            LoggerManager.getLog().info("Los parametros recibidos son: usuario=" + usuario + " y password=" + password);
        }

        //declaro un objeto de tipo HttpSession
        HttpSession session = request.getSession();

        //si los parametros estan bien definidos
        if (usuario != null && !usuario.equals("") && password != null && !password.equals("")) {

            tipoUsuario = controlaCrendencialesUsuario(usuario, password);

            //añado el usuario a la session
            session.setAttribute("usuario", tipoUsuario);

            // si el usuario es el administrador, lo reenviamos a una pagina donde 
            //puede crear coches y motos
            if (tipoUsuario.equals("admin")) {

                path = "/index.jsp";
            } //sino visualizamos los coche y las motos
            else {

                path = "/VisualizaDatosServlet";
            }

            if (LoggerManager.DEBUG) {
                LoggerManager.getLog().info("El usuario se ha autenticado "
                        + "como " + tipoUsuario);
            }

        } //sino volvamos al login.jsp
        else {
            if (LoggerManager.DEBUG) {
                LoggerManager.getLog().error("Los parametros estan mal definido...!!!");
            }

            path = "/login.jsp";

        }
        request.getRequestDispatcher(path).forward(request, response);

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

    /**
     *
     * @param usuario
     * @param password
     * @return
     */
    private String controlaCrendencialesUsuario(String usuario, String password) {
        String tmp = null;
        // TODO codigo para controlar si existe el usuario con password en el database
        tmp = usuario;
        return tmp;
    }

}
