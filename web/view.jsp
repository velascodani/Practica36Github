<%-- 
    Document   : view
    Created on : 16-may-2014, 11:36:40
    Author     : FO-Mañana
--%>

<%@page errorPage="error.jsp" language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Página de resultados</title>
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <h2 class="well">Lista de <code>categoria</code> y sus <code>vehiculos</code></h2>
        <c:choose>
            <c:when test="${categoriaList == null}">
                <p>FATAL ERROR!</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="categoria" items="${categoriaList}">
                    <c:forEach var="vehiculo" items="${categoria.listaVehiculos}">
                        <c:if test="${vehiculo.nrDeRuedas==2}"><div class="panel panel-success"></c:if>
                        <c:if test="${vehiculo.nrDeRuedas==4}"><div class="panel panel-info"></c:if>
                    <div class="panel-heading">
                        <h1 class="panel-title">
                            <b>Categoría:</b> ${categoria.tipo} 
                            <small>${categoria.descripcion}</small> 
                        </h1>
                    </div>
                    
                    <div class="panel-body">
                        <p><b>Matricula:</b> ${vehiculo.matricula}</p>
                        <p><b>Color:</b> ${vehiculo.color}</p>
                        <p><b>Fabricante:</b> ${vehiculo.fabricante}</p>
                        <p><b>Cilindros:</b> ${vehiculo.motor.cilindros}</p>
                        <p><b>Potencia:</b> ${vehiculo.motor.potencia}</p>
                    </div>
                    <div class="panel-footer">
                        
                            <c:if test="${vehiculo.nrDeRuedas==2}"><span class="text-success">Moto</span></c:if>
                            <c:if test="${vehiculo.nrDeRuedas==4}"><span class="text-info">Coche</span></c:if>
                        
                    </div>
                    
                    </div>
                        </c:forEach>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </div>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    </body>
</html>
