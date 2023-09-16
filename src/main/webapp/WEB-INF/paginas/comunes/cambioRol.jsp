<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="servicios">
    <c:if test="${usuario.solicitud == null}">
        <h3>Rol Actual: ${usuario.tipo}</h3>
        <table class="tabla">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Rol Solicitado</th>
                                <th>Solicitar Rol</th>
                            </tr>
                        </thead>
                        <tbody>     
                            <c:forEach var="tipo" items="${tipos}">
                                <tr>
                                    <td>#</td>
                                    <td>${tipo}</td>
                                    <td>
                                        <div>
                                            <a href="${pageContext.request.contextPath}/Controlador?accion=peticionRol&rol=${tipo}" >
                                                <input class="boton-varios" type="submit" value="Registrar Peticion"/>
                                            </a> 
                                        </div>
                                    </td>
                                </tr> 
                            </c:forEach>                               
                        </tbody>
                    </table>
    </c:if>
    <c:if test="${usuario.solicitud != null}">
        <h2>Estado de la solicitud</h2>
        <table class="tabla">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Rol Actual</th>
                    <th>Rol Solicitado</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>              
                    <tr>
                        <td>${status.count}</td>
                        <td>${usuario.tipo}</td>
                        <td>${usuario.solicitud}</td>
                        <td>
                            <c:choose>
                                <c:when test="${usuario.tipo == usuario.solicitud}">
                                Aceptado
                                </c:when>
                                <c:when test="${usuario.tipo != usuario.solicitud}">
                                Pendiente
                                </c:when>
                                <c:when test="${usuario.solicitud == 'Rechazado'}">
                                Rechazado
                                </c:when>
                            </c:choose>
                        </td>
                        
                    </tr>
            </tbody>
        </table>
    </c:if>                   
</div>