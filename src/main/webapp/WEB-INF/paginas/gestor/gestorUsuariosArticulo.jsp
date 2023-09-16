<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Wikis</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/gestor/gestorMenu.jsp"></jsp:include>

            <!-- Listado wikis Gestor-->
            <main class="contenedor sombra">
                
            

                
            <c:if test="${empty articulo}">
                <h3>No existen elementos disponibles</h3>
            </c:if>
            <c:if test="${!empty articulo}">
                
                <jsp:include page="/WEB-INF/paginas/comunes/articuloDesplegado.jsp"></jsp:include>
                
                <h3>Coordinadores y Supervisores del Articulo: ${articulo.tituloArticulo}</h3>
                <div class="servicios">
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
                                            <a href="${pageContext.request.contextPath}/Controlador?accion=eliminarRolArticulo&idUsuario=${usuario.id}" >
                                                <input class="boton-varios" type="submit" value="Eliminar"/>
                                            </a> 
                                        </div>
                                    </td>
                                </tr>

                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!<!-- Lista de Usuarios para ser Coordinador y Supervisores -->
                <section>
                    <h3> Selecciona los Coordinadores y Supervisores</h3>
                    <form action="${pageContext.request.contextPath}/Controlador?accion=addUserArticulo"
                          method="POST" class="formulario">
                        <fieldset>
                            <div>
                                <div>
                                    <legend>Lista de Coordinadores</legend>
                                    <c:forEach var="candidato" items="${candidatos}" varStatus="status">
                                        <c:if test = "${candidato.tipo == 'Coordinador'}">
                                            <input type="checkbox" id="${candidato.id}" name="checkbox" value="${candidato.id}">
                                            <label class="negrita" for="${candidato.id}">${candidato.nombre}  ${candidato.apellido}</label><br>
                                        </c:if>
                                    </c:forEach> 
                                </div>
                                <br>
                                <div>
                                    <legend>Lista de Supervisores</legend>
                                    <c:forEach var="candidato" items="${candidatos}" varStatus="status">
                                        <c:if test = "${candidato.tipo == 'Supervisor'}">
                                            <input type="checkbox" id="${candidato.id}" name="checkbox" value="${candidato.id}">
                                            <label class="negrita" for="${candidato.id}">${candidato.nombre}  ${candidato.apellido}</label><br>
                                        </c:if>
                                    </c:forEach> 
                                            <br>
                                </div>                               
                                    <input class="boton" type="submit" value="Enviar"/>
                                </div>
                            </div>

                        </fieldset>
                    </form>
                </section>

            </c:if>


        </main>

        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>
