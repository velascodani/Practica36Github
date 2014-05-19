/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.Categoria;
import beans.Coche;
import beans.Moto;
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
import managers.DatabaseManager;
import managers.LoggerManager;

/**
 * En esta servlet se enseña como guardar objetos de tipo Categoria en una lista
 * de categoria.
 *
 * El servlet recibe la peticion desde un formulario html enviado desde una .jsp
 *
 */
public class ServletClaseCreacion extends HttpServlet {

    private final String COCHE_ID = "1";
    private final String MOTO_ID = "2";
    /*
     propriedad de la clase ServletClaseCreacion, lista de categoriasList
     se necesita para guardar la categoriasList y los coches que se van creando en la servlet
     */
    private ArrayList<Categoria> categoriasList;

    @Override
    public void init() throws ServletException {
        super.init();
        categoriasList = new ArrayList<Categoria>();
    }

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
        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

        //guardo los parametros enviados por el formulario
        String tipo = request.getParameter("tipo");
        String descripcion = request.getParameter("descripcion");
        String matricula = request.getParameter("matricula");
        String color = request.getParameter("color");
        String fabricante = request.getParameter("fabricante");
        String valocidadMax = request.getParameter("velocidadMax");
        String tipoVehiculo = request.getParameter("vehiculo");
        String potencia = request.getParameter("potencia");
        String cilindros = request.getParameter("cilindros");

        //controlo que los datos esten bien definidos
        if ((tipo != null && !tipo.equals("")) && (descripcion != null && !descripcion.equals(""))
                && (matricula != null && !matricula.equals("")) && (color != null && !color.equals(""))
                && (fabricante != null && !fabricante.equals("")) && (valocidadMax != null && !valocidadMax.equals(""))
                && (tipoVehiculo != null && !tipoVehiculo.equals("")) && (potencia != null && !potencia.equals(""))
                && (cilindros != null && !cilindros.equals(""))) {

            //si estan bien definidos, los processo
            //hago el parsing de un string a un int (el parametro velocidadMax tiene que ser un intero)
            int velocidadMaxInt = Integer.parseInt(valocidadMax);

            //creo una instancia/objeto de tipo categoria con los parametros enviado por el formulario
            Categoria categoria = new Categoria(tipo, descripcion);
            //creo una instancia/objeto de tipo coche con los parametro enviados por el formulario

            Coche coche = null;
            Moto moto = null;
            if (tipoVehiculo.equals("Coche")) {
                coche = new Coche(matricula, color, fabricante, velocidadMaxInt, potencia, cilindros);
            }
            if (tipoVehiculo.equals("Moto")) {
                moto = new Moto(matricula, color, fabricante, velocidadMaxInt, potencia, cilindros);
            }

            //ahora, lo que no quiero hacer es añadir una categoria a la lista si ésta
            //ya existe en la lista
            //para poder hacer esto, busco la categoria (por tipo) en la lista categoriasList
            int index = -1;

            //llamo una funcion que busca una categoria por tipo en la base de datos
            index = getCategoria(tipo);

            //si la categoria no existe
            if (index == -1) {

                //controlo que tipo de vehiculo queiro añadir
                if (tipoVehiculo.equals("Coche")) {
                        //añado el coche y la categoria a la base de datos
                    //categoria.addVehiculo(coche);
                    añadirCategoriaYVehiculo(categoria, coche);
                }
                if (tipoVehiculo.equals("Moto")) {
                        //añado la moto y la categoria a la base de datos
                    //categoria.addVehiculo(moto);
                    añadirCategoriaYVehiculo(categoria, moto);
                }
            } //si la categoria existe
            else {

                if (tipoVehiculo.equals("Coche")) {

                    //añado el coche a la base de datos con categoria index
                    añadoVehiculo(index, coche, true);

                }
                if (tipoVehiculo.equals("Moto")) {
                    //añado la moto  a la base de datos con categoria index
                    añadoVehiculo(index, moto, true);
                }
            }

            request.getRequestDispatcher("/VisualizaDatosServlet").forward(request, response);

                //fin if, sino vuelvo a la pagina index.jsp    
        } else {
            //instrucion para volver a una index.jsp
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        DatabaseManager.closeConnection();

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

    /*
     * metodo que busca una categoria por tipo
     * devuelve -1 si la categoria no existe, 
     la posicion de la categoria si existe 
     */
    private int getCategoria(String tipo) {

        int index = -1;
        //declaro los objetos Java necesarios para ejecutar la query
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //declaro la query
            String categoriaSql = "SELECT categoria.id FROM categoria WHERE categoria.tipo='" + tipo + "'";

            //preparo la query a la base de datos
            preparedStatement = DatabaseManager.conn.prepareStatement(categoriaSql);
            //ejecuto la query
            resultSet = preparedStatement.executeQuery();
            //proceso el resultado
            while (resultSet.next()) {
                //guardo el id de la categoria en la variable index
                index = resultSet.getInt("id");
            }
            preparedStatement.close();
            resultSet.close();

            //cierro los objetos de Java necesarios para ejecutar la query
            //en el caso haya en error en el codigo de antes, salto aqui
        } catch (SQLException ex) {
            //imprimo un mensaje de error en el logger
            LoggerManager.getLog().error(ex.toString());
            preparedStatement.close();
            resultSet.close();
        } finally {

            return index;
        }

    }

    /**
     * esta funcion añade una categoria, un motor y un vehiculo a la base de
     * datos
     *
     * @param categoria
     * @param vehiculo
     */
    private void añadirCategoriaYVehiculo(Categoria categoria, Vehiculo vehiculo) {
        
        String categoriaSql = "INSERT INTO categoria (tipo,descripcion) VALUES " +
                " ('"+categoria.getTipo()+"','"+categoria.getDescripcion()+"') ";
        
      //  LoggerManager.getLog().info(categoriaSql);
        try {
            //desabilitar el autoCommit
            DatabaseManager.conn.setAutoCommit(false);
            
            //ejecutar la query y guardar el id de la categoria
            int categoriaId = DatabaseManager.executeUpdate(categoriaSql);
            
            //llamar la funcion añadirVehiculo con el id de la categoria y el vehiculo
            boolean hacerCommit = false;
            añadoVehiculo(categoriaId, vehiculo,hacerCommit);
            
            //hago el commit
            DatabaseManager.conn.commit();
            
            
        } catch (SQLException ex) {
            try {
                LoggerManager.getLog().error(ex.toString());
                DatabaseManager.conn.rollback();  
            } catch (SQLException ex1) {
                LoggerManager.getLog().error(ex1.toString());
            }
            
        } finally {
            try {
                DatabaseManager.conn.setAutoCommit(true);
            } catch (SQLException ex) {
                LoggerManager.getLog().error(ex.toString());
            }
        }
        
    }

    /**
     * esta funcion añade un motor y un vehiculo a la base de datos
     *
     * @param categoriaIndex
     * @param vehiculo
     */
    private void añadoVehiculo(int categoriaIndex, Vehiculo vehiculo, boolean hacerCommit) {
        //categoriaIndex es el id de la categoria

        //para añadir un vehiculo tenemos que añadir el motor antes
        String motorSql = "INSERT INTO motor (potencia,cilindros) VALUES "
                + "('" + vehiculo.getMotor().getPotencia() + "','" + vehiculo.getMotor().getCilindros() + "')";

        String vehiculoSql = "INSERT INTO vehiculo (matricula,fabricante,color,velocidadMax,categoria_id,motor_id,vehiculo_tipo_id) VALUES "
                + "('" + vehiculo.getMatricula() + "','" + vehiculo.getFabricante() + "','" + vehiculo.getColor() + "'," + vehiculo.getVelocidadMax() + "," + categoriaIndex + ",MOTOR_ID,VEHICULO_TIPO_ID) ";

        
        if (vehiculo instanceof Coche) {
            //llamo replaceAll para cambiar el VEHICULO_TIPO_ID con el id del tipo de vehiculo
            vehiculoSql = vehiculoSql.replaceAll("VEHICULO_TIPO_ID", COCHE_ID);
        }
        if (vehiculo instanceof Moto) {
            //llamo replaceAll para cambiar el VEHICULO_TIPO_ID con el id del tipo de vehiculo
            vehiculoSql = vehiculoSql.replaceAll("VEHICULO_TIPO_ID", MOTO_ID);
        }
       // LoggerManager.getLog().info(motorSql);
        try {
            //vamos a hacer una transacion para insertar un motor y un vehiculo
            //1. desabilitar el autoCommit
            DatabaseManager.conn.setAutoCommit(false);
            
            //2. llamar una funcion que ejecuta una INSERT sql
            //y me devuelve el id del elemento añadido a la base de datos
            int motorId = DatabaseManager.executeUpdate(motorSql);

            //3. completar la INSERT vehiculoSql cambiando el MOTOR_ID 
            //por el id devuelto por la funcion anterior
            String motorIdTmp = String.valueOf(motorId);
            vehiculoSql = vehiculoSql.replaceAll("MOTOR_ID",motorIdTmp);
           // LoggerManager.getLog().info(vehiculoSql);
            
            //4. llamar la executeUpdate(vehiculoSql)
            DatabaseManager.executeUpdate(vehiculoSql);
            
            //5. hacer commit de la transacion
            if (hacerCommit) {
                DatabaseManager.conn.commit();
            }
           
            
        } catch (SQLException ex) {
            try {
                //en el caso que tenga exception, hago rollback
                LoggerManager.getLog().error(ex.toString());
                DatabaseManager.conn.rollback();
                
            } catch (SQLException ex1) {
                 LoggerManager.getLog().error(ex1.toString());
            }
           

        } finally {

            try {
                if (hacerCommit)
                    DatabaseManager.conn.setAutoCommit(true);
            } catch (SQLException ex) {
                LoggerManager.getLog().error(ex.toString());
            }

        }
    }

}
