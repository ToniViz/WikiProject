<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Solicitudes</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/gestor/gestorMenu.jsp"></jsp:include>

            <!-- Listado solicitudes de cambio de rol-->
            <main class="contenedor sombra">
                <div class="servicios">
                    <h2>Peticiones de cambio de rol</h2>
                <c:if test="${empty usuariosS}">
                    <h3>No existen elementos disponibles</h3>
                </c:if>
                <c:if test="${!empty usuariosS}">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nombre</th>
                                <th>Rol Actual</th>
                                <th>Rol Propuesto</th>
                                <th colspan="2">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="usuario" items="${usuariosS}" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td>${usuario.nombre} ${usuario.apellido}</td>
                                    <td>${usuario.tipo}</td>
                                    <td>${usuario.solicitud}</td>
                                    <td>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/Controlador?accion=aceptarSol&idUsuario=${usuario.id}" >
                                                <input class="boton-varios" type="submit" value="Aceptar"/>
                                            </a>       
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/Controlador?accion=rechazarSol&idUsuario=${usuario.id}" >
                                                <input class="boton-varios" type="submit" value="Rechazar"/>
                                            </a> 
                                        </div>
                                    </td>
                                </tr>

                            </c:forEach>
                        </tbody>
                    </table>
                </c:if> 
                <br>
                <h2 class="margin-top ">Coordinadores, supervisores y colaboradores del sistema</h2>
                <c:if test="${empty usuariosI}">
                    <h4>No existen elementos disponibles</h4>
                </c:if>
                <c:if test="${!empty usuariosI}">
                    <div>
                        <div class="margin-top">
                            <table class="tabla">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Usuario</th>
                                        <th>Rol</th>
                                        <th>Eliminar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="usuario" items="${usuariosI}" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td>${usuario.nombre} ${usuario.apellido}</td>
                                            <td>${usuario.tipo}</td>
                                            <td>
                                                <div>
                                                    <a href="${pageContext.request.contextPath}/Controlador?accion=eliminar&idUsuario=${usuario.id}" >
                                                        <input class="boton-varios" type="submit" value="Eliminar"/>
                                                    </a> 
                                                </div>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>    
                    </div>
                </c:if>    



            </div>                    

        </main>

        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>