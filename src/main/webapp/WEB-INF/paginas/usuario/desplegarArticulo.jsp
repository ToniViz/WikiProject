<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="recursos/normalize.css" rel="stylesheet" type="text/css">
        <link href="recursos/style.css" type="text/css" rel="stylesheet" >


        <title>Articulo</title>
    </head>
    <body>

        <jsp:include page="/WEB-INF/paginas/comunes/cabecero.jsp"></jsp:include>

        <jsp:include page="/WEB-INF/paginas/usuario/usuarioMenu.jsp"></jsp:include>

            <!-- Articulo -->
            <main class="contenedor sombra">
            <jsp:include page="/WEB-INF/paginas/comunes/articuloDesplegado.jsp"></jsp:include>
            <div class="centrar">
                    <a href="registrarse.jsp" >
                    <input class="boton" type="submit" value="Registrarse"/>
                </a> 
            </div>
        </main>


        <!-- Pie de pagina -->
        <jsp:include page="/WEB-INF/paginas/comunes/piedePagina.jsp"></jsp:include>




    </body>
</html>
