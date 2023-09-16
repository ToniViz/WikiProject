<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Articulos Supervisor</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/supervisor/supervisorMenu.jsp"></jsp:include>

            <!-- Listado Articulos -->
            <main class="contenedor sombra">
                <h2>Artículos sin derechos de edición</h2>
            <jsp:include page="/WEB-INF/paginas/comunes/listarArticulos.jsp"></jsp:include>

                <section>
                    <h3>Artículos con derechos de edición</h3>
                    <div class="servicios">
                    <c:if test="${empty revisiones}">
                        <h3>No existen elementos disponibles</h3>
                    </c:if>
                    <c:if test="${revisiones.size() > 0}">
                        <div>
                            <h3>Articulos a revisar</h3> 
                            <table class="tabla">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Titulo</th>
                                        <th>Revisar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="revision" items="${revisiones}" varStatus="status">
                                        <tr>
                                            <td>${status.count}</td>
                                            <td>${revision.tituloArticulo}</td>
                                            <td class="boton-contenedor">
                                                <div class="boton-varios">
                                                    <a href="${pageContext.request.contextPath}/Controlador?accion=mostrarModificado&idArticulo=${revision.id}" >
                                                        <input class="boton" type="submit" value="Revisar"/>
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
            </section>


        </main>




        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>