<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="contenedor-articulo contenido-principal ">
    <div>
        <h2 class="azul">${articulo.tituloArticulo}</h2>

        <c:forEach var="contenido" items="${contenidos}">
            <article class="entrada">
                <div>
                    <h4 class="no-margin">${contenido.subtitulo}</h4>
                    <p>${contenido.frases}</p>
                </div>
            </article>
        </c:forEach>
    </div>
        <aside>
            <h3 class="negrita azul">${articulo.tituloArticulo}</h3>
            <ul class="articulos no-padding">
                <li class="widget-articulo">
                    <h5 class="no-margin">Descripción:</h5>
                    <b class="widget-articulo__label">${articulo.descripcion}</b>
                </li>
            </ul>
        </aside>
</section>
