<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Articulo Supervisor</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/supervisor/supervisorMenu.jsp"></jsp:include>

            <!-- Artículo desplegado -->
            <main class="contenedor sombra">

            <jsp:include page="/WEB-INF/paginas/comunes/articuloDesplegado.jsp"></jsp:include>


            <c:if test="${!empty cambios}">
                <section>
                    <h3>Lista de modificaciones (cambios en la edición mostrados en gris)</h3>
                    <form action="${pageContext.request.contextPath}/Controlador?accion=cambiosArticulo"
                          method="POST" class="">
                        <fieldset>
                            <div>
                                <div>
                                   <table class="tabla">
                                        <thead>
                                            <tr>
                                                <th>#</th>
                                                <th>Original</th>
                                                <th>Cambio Propuesto</th>
                                                <th>Cambio Propuesto</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach var="cambio" items="${cambios}" varStatus="status">
                                                <tr>

                                                    <td>${status.count}</td>
                                                    <td>${cambio.key}</td>
                                                    <td>${cambio.value}</td>   
                                                    <td>
                                                        <div>
                                                            <input type="checkbox" name="checkbox" value="${cambio.value}">
                                                            <label class="negrita" for="checkbox">Aceptar Cambio</label>
                                                        </div> 
                                                    </td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                                             
                                </div>
                                <div>
                                    <input class="boton" type="submit" value="Confirmar"/>
                                </div>
                            </div>

                        </fieldset>
                    </form>
                    <div>
                        <a href="${pageContext.request.contextPath}/Controlador?accion=descartar&idArticulo=${articulo.id}" >
                            <input class="boton-varios" type="submit" value="Descartar Todo"/>
                        </a>
                    </div>      
                </section>  
            </c:if>


            <jsp:include page="/WEB-INF/paginas/comunes/botonesCargaDescarga.jsp"></jsp:include>

                <div class="servicios">
                    <div>
                        <h3>Usuarios que pueden acceder al contenido</h3>
                    </div>
                <c:if test="${empty usuarios}">
                    <h3>No existen colaboradores</h3>

                </c:if>
                <c:if test="${usuarios.size() > 0}">
                    <div>
                        <table class="tabla">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Nombre del Usuario</th>
                                    <th>Vetar Acceso</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="usuario" items="${usuarios}" varStatus="status">
                                    <tr>

                                        <td >${status.count}</td>
                                        <td>${usuario.nombre} ${usuario.apellido}</td>
                                        <td >
                                            <div>
                                                <a href="${pageContext.request.contextPath}/Controlador?accion=vetarUsuario&idUsuario=${usuario.id}" >
                                                    <input class="boton-varios" type="submit" value="Vetar"/>
                                                </a>                    
                                            </div>
                                        </td>


                                    </tr>
                                </c:forEach>
                            </tbody>



                        </table>
                    </div>

                </c:if>    

                <div>
                    <div>
                        <h3>Usuarios vetados</h3>
                    </div>
                    <c:if test="${empty usuariosV}">
                        <h3>No existen colaboradores</h3>

                    </c:if>
                    <c:if test="${usuariosV.size() > 0}">
                        <div>
                            <table class="tabla">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Nombre del Usuario</th>
                                        <th>Vetar Acceso</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="usuario" items="${usuariosV}" varStatus="status">
                                        <tr>

                                            <td >${status.count}</td>
                                            <td>${usuario.nombre} ${usuario.apellido}</td>
                                            <td >
                                                <div>
                                                    <a href="${pageContext.request.contextPath}/Controlador?accion=deleteVeto&idUsuario=${usuario.id}" >
                                                        <input class="boton-varios" type="submit" value="Eliminar Veto"/>
                                                    </a>                    
                                                </div>
                                            </td>


                                        </tr>
                                    </c:forEach>
                                </tbody>



                            </table>
                        </div>

                    </c:if>    
                </div>


            </div>



        </main>


        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>
