<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Listado modificaciones</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/colaborador/colaboradorMenu.jsp"></jsp:include>

            <!-- Listado Modificaciones -->
            <main class="contenedor sombra">

                <h2>Estado de la solicitud</h2>
            <c:if test="${empty solicitudes}">
                <h3>No existen modificaciones</h3>
            </c:if>
            <c:if test="${solicitudes.size() > 0}">
                <table class="tabla">
                    <thead>
                        <tr>                          
                            <th>Titulo</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody> 
                        <c:forEach var="solicitud" items="${solicitudes}" varStatus="status">

                        <td>${solicitud.key}</td>
                        <td>${solicitud.value}</td>

                    </c:forEach>         


                    </tbody>
                </table>
            </c:if>


        </main>




        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>