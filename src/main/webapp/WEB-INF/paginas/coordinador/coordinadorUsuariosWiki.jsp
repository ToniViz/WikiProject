<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Usuarios Wiki</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/coordinador/coordinadorMenu.jsp"></jsp:include>

            <!-- Listado wikis Coordinador-->
            <main class="contenedor sombra">


            <c:if test="${empty wiki}">
                <h3>No existen elementos disponibles</h3>
            </c:if>
            <c:if test="${!empty wiki}">
                <h3>Supervisores de la Wiki: ${wiki.titulo}</h3>

                <div class="servicios">
                    <c:if test="${empty usuarios}">
                        <h3>No existen elementos disponibles</h3>
                    </c:if>
                    <c:if test="${!empty usuarios}">
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
                                <c:forEach var="usuario" items="${usuarios}" varStatus="status">
                                    <tr>
                                        <td>${status.count}</td>
                                        <td>${usuario.nombre} ${usuario.apellido}</td>
                                        <td>${usuario.tipo}</td>
                                        <td>
                                            <div>
                                                <a href="${pageContext.request.contextPath}/Controlador?accion=deleteWikiUsuario&usuarioId=${usuario.id}" >
                                                    <input class="boton-varios" type="submit" value="Eliminar"/>
                                                </a> 
                                            </div>
                                        </td>
                                    </tr>

                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>    


                </div>
                <!-- Lista de Usuarios para ser Supervisor -->
                <section>
                    <h3>Seleccione los Supervisores</h3>
                    <c:if test="${empty candidatos}">
                        <h3>No existen elementos disponibles</h3>
                    </c:if>
                    <c:if test="${!empty candidatos}">
                        <form action="${pageContext.request.contextPath}/Controlador?accion=addUsuarioWiki"
                              method="POST" class="formulario">
                            <fieldset>
                                <div>
                                    <div>                                  
                                        <c:forEach var="candidato" items="${candidatos}" varStatus="status">

                                            <input type="checkbox" name="checkbox" value="${candidato.id}">
                                            <label class="negrita" for="${candidato.id}">${candidato.nombre}  ${candidato.apellido}</label><br>

                                        </c:forEach> 
                                    </div>
                                    <div>
                                        <input class="boton" type="submit" value="Agregar"/>
                                    </div>
                                </div>

                            </fieldset>
                        </form>
                    </c:if> 

                </section>           
            </c:if>


        </main>

        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>