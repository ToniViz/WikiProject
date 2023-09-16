<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="servicios">
    <c:if test="${empty articulos}">
        <h3>No existen elementos disponibles</h3>
    </c:if>
    <c:if test="${articulos.size() > 0}">
        <h2>Lista de Articulos</h2>
        <table class="tabla">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Titulo</th>
                    <th>Descripcion</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="articulo" items="${articulos}" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>

                            <a href="${pageContext.request.contextPath}/Controlador?accion=mostrarArticulo&idArticulo=${articulo.id}"
                               class="opcion">
                                ${articulo.tituloArticulo}
                            </a>
                        </td>
                        <td>${articulo.descripcion}</td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
    </c:if>                   
</div>