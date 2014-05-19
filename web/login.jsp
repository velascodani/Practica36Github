<%-- 
    Document   : login
    Created on : 6-mag-2014, 10.29.03
    Author     : confalonieri
--%>

<%@page errorPage="error.jsp" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina de login de la aplicacion</title>
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-4 col-xs-offset-4">
        <h1>Pagina de Login</h1>
        
        <form class="form" method="post" action="login">
            
            <label for="usuario">Nombre</label>
            <input class="form-control" type="text" name="usuario">
            <br>
            
            <label for="password">Password</label>
            <input class="form-control" type="password" name="password">
            <br>
            
            <input class="btn btn-primary" type="submit" value="Login">
            <br>  
        </form>
                </div></div></div>
    </body>
</html>
