<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>          
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="boton-contenedor">
    <div>
        <h3>El archivo modificado debe estar estructurado siguiendo las mismas pautas del archivo descargado</h3>
        <a href="${pageContext.request.contextPath}/Controlador?accion=descargar&idArticulo=${articulo.id}" >
            <input class="boton-varios" type="submit" value="Descargar"/>
        </a>
    </div>

    <c:if test="${articulo.archivo == null}">
        <form action="${pageContext.request.contextPath}/Controlador?accion=cargar" method="POST" enctype="multipart/form-data">
            <input type="file" id="archivo" name="archivo">
            <input class="boton-varios" type="submit" value="Enviar">
        </form>
    </c:if>
    <c:if test="${fn:length(articulo.archivo) > 50}">
        <h3>El artículo se encuentra en proceso de evaluación</h3>
    </c:if>
</div>

