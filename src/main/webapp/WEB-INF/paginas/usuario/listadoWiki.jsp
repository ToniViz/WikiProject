<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<main class="contenedor sombra">
    <c:if test="${empty wikis}">
        <h3>No existen elementos disponibles</h3>
    </c:if>
    <c:if test="${wikis.size() > 0}">
        <h2>Listado de Wikis</h2>
        <div class="servicios">
        <table class="tabla">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Titulo</th>
                    <th>Descripcion</th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="wiki" items="${wikis}" varStatus="status">
                    <tr>

                        <td>${status.count}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/Controlador?accion=listarArticulo&idWiki=${wiki.id}"
                               class="opcion">
                                ${wiki.titulo}
                            </a>
                        </td>
                        <td>${wiki.tematica}</td>

                    </tr>
                </c:forEach>
            </tbody>



        </table>
    </div>
    </c:if>    
    
    
</main>