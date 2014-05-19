/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import managers.DatabaseManager;
import managers.LoggerManager;

/**
 *
 * @author confalonieri
 */
public class Categoria {

    private int id;
    private String tipo;
    private String descripcion;
    private ArrayList<Vehiculo> listaVehiculos;
    
    public Categoria(String tipo, String descripcion) {

        this.tipo = tipo;
        this.descripcion = descripcion;
        this.listaVehiculos = new ArrayList<Vehiculo>();
       

    }

    public Categoria(int id, String tipo, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }
    
    
    
    public void addVehiculo(Vehiculo vehiculo) {
        
        this.listaVehiculos.add(vehiculo);
    }

    public Categoria() {

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        String categoriaString;

        categoriaString = "[Tipo: " + this.tipo + "]<br>"
                + "[Descripcion: " + this.descripcion + "]<br>"
                + this.listaVehiculos.toString() +"]<br>";
                

        return categoriaString;
    }

    public ArrayList<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }

    public void crearListaVehiculos(int id) {
        this.listaVehiculos = new ArrayList<Vehiculo>();
        
        //creamos la query
        String vehiculoSql = "SELECT * " +
                             "FROM vehiculo,motor,vehiculo_tipo " +
                             "WHERE vehiculo.motor_id=motor.id  " +
                               "AND vehiculo.vehiculo_tipo_id=vehiculo_tipo.id " +
                               "AND vehiculo.categoria_id="+id;
        
        LoggerManager.getLog().info(vehiculoSql);
        //declaro los objetos Java para la query
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            //ejecutar la query
            preparedStatement = DatabaseManager.conn.prepareStatement(vehiculoSql);
            resultSet = preparedStatement.executeQuery();
            
            //declaro un objecto coche y moto
            Coche coche;
            Moto moto;
            
            //processar el resultado
            while (resultSet.next()) {
                //guardo todos los valores de las columnas del resultSet
                String matricula = resultSet.getString("matricula");
                String fabricante = resultSet.getString("fabricante");
                String color = resultSet.getString("color");
                int velocidadMax = resultSet.getInt("velocidadMax");
                String potencia = resultSet.getString("potencia");
                String cilindros = resultSet.getString("cilindros");
                String tipoVehiculo = resultSet.getString("tipo");
                //creo un coche o una moto
                if (tipoVehiculo.equals("Coche")) {
                    
                    coche = new Coche(matricula, color, fabricante, velocidadMax, potencia, cilindros);
                    listaVehiculos.add(coche);
                }
                
                if (tipoVehiculo.equals("Moto")) {
                    
                    moto = new Moto(matricula, color, fabricante, velocidadMax, potencia, cilindros);
                    listaVehiculos.add(moto);
                }
            } 
           
            //cerrar los objetos Java de la query
            preparedStatement.close();
            resultSet.close();
            
        } catch (SQLException ex) {
            LoggerManager.getLog().error(ex.toString());
        }
        
    }

}
