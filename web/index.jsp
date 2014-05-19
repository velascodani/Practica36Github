<%-- 
    Document   : index
    Created on : 23-apr-2014, 9.24.53
    Author     : confalonieri
--%>



<%@page errorPage="error.jsp" language="java" contentType="text/html" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Practica Clases</h1>
        <form method="post" action="ServletClaseCreacion">
            <p>Categoria
                <label for="tipo">Tipo:</label>
                <input type="text" name="tipo">
                <br>

                <label for="descripcion">Descripcion:</label>
                <input type="text" name="descripcion">
                <br>
            </p>


            <p>
                <select name="vehiculo">
                    <option value="Coche">Coche</option>
                    <option value="Moto">Moto</option>
                </select>   

                <label for="matricula">Matricula:</label>
                <input type="text" name="matricula">
                <br>

                <label for="color">Color:</label>
                <input type="text" name="color">
                <br>

                <label for="fabricante">Fabricante:</label>
                <input type="text" name="fabricante">
                <br>

                <label for="velocidadMax">VelocidadMax:</label>
                <input type="text" name="velocidadMax">
                <br>
                
                 <label for="potencia">Potencia</label>
                <input type="text" name="potencia">
                <br>
                
                 <label for="cilindros">Cilindros</label>
                <input type="text" name="cilindros">
                <br>
            </p>

            <input type="submit" value="envia">
        </form>
    </body>
</html>
