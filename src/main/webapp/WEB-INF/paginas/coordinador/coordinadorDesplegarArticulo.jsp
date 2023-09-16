<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Lista Articulos</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/coordinador/coordinadorMenu.jsp"></jsp:include>

            <!-- Articulo depslegado-->
            <main class="contenedor sombra">


            <jsp:include page="/WEB-INF/paginas/comunes/articuloDesplegado.jsp"></jsp:include>

            <div>
                <a href="${pageContext.request.contextPath}/Controlador?accion=addContent&idArticulo=${articulo.id}" >
                    <input class="boton-varios" type="submit" value="Agregar Contenido"/>
                </a>
            </div>


        </main>

        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>