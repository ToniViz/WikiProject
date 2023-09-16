<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Listar artículos</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/coordinador/coordinadorMenu.jsp"></jsp:include>

            <!-- Listado artículos -->
            <main class="contenedor sombra">

                <h2>Artículos de la Wiki</h2>
                <div class="servicios">
                <c:if test="${empty articulos}">
                    <h2>No existen elementos</h2>
                </c:if>
                <c:if test="${articulos.size() > 0}">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Titulo</th>
                                <th colspan="4">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="articulo" items="${articulos}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${articulo.tituloArticulo}</td>
                            <td>
                                <div>
                                    <a href="${pageContext.request.contextPath}/Controlador?accion=modificar&idArticulo=${articulo.id}" >
                                        <input class="boton-varios" type="submit" value="Modificar"/>
                                    </a>                    
                                </div>
                            </td>
                            <td>
                                <div>
                                    <a href="${pageContext.request.contextPath}/Controlador?accion=deleteArticle&idArticulo=${articulo.id}" >
                                        <input class="boton-varios" type="submit" value="Borrar"/>
                                    </a>
                                </div>
                            </td>
                            <td>
                                <div>
                                    <a href="${pageContext.request.contextPath}/Controlador?accion=gestionarArticulo&idArticulo=${articulo.id}" >
                                        <input class="boton-varios" type="submit" value="Usuarios"/>
                                    </a>
                                </div>
                            </td>
                            <td>
                                <div>
                                    <a href="${pageContext.request.contextPath}/Controlador?accion=mostrarArticulo&idArticulo=${articulo.id}" >
                                        <input class="boton-varios" type="submit" value="Acceder"/>
                                    </a>
                                </div>
                            </td>
                        </tr>

                    </c:forEach>
                    </tbody>
                </table>
                </c:if>
                    

            </div>
        </main>

        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>
