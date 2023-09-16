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

        <jsp:include page="/WEB-INF/paginas/coordinador/coordinadorMenu.jsp"></jsp:include>

            <!-- Listado wikis Coordinador-->
            <main class="contenedor sombra">

                <h2>Menu Coordinador: Wikis</h2>
                <div class="servicios">
                <c:if test="${empty wikis}">
                    <h2>No existen elementos asignados</h2>
                </c:if>
                <c:if test="${wikis.size() > 0}">
                    <table class="tabla">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Titulo</th>
                                <th colspan="3">Opciones</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="wiki" items="${wikis}" varStatus="status">
                        <tr>
                            <td class="negrita-centrado">${status.count}</td>
                            <td class="negrita-centrado">${wiki.titulo}</td>
                            <td>
                                <div>
                                    <a href="${pageContext.request.contextPath}/Controlador?accion=agregar&idWiki=${wiki.id}" >
                                        <input class="boton-varios" type="submit" value="Crear Articulo"/>
                                    </a>                    
                                </div>
                            </td>
                            <td>
                                <div>
                                    <a href="${pageContext.request.contextPath}/Controlador?accion=gestionar&idWiki=${wiki.id}" >
                                        <input class="boton-varios" type="submit" value="Usuarios"/>
                                    </a>
                                </div>
                            </td>
                            <td>
                                <div>
                                    <a href="${pageContext.request.contextPath}/Controlador?accion=listarArticulos&idWiki=${wiki.id}" >
                                        <input class="boton-varios" type="submit" value="Articulos"/>
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
